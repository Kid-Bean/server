package soongsil.kidbean.server.quiz.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.global.vo.ImageInfo;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quiz.domain.ImageQuiz;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUpdateRequest;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizUploadRequest;
import soongsil.kidbean.server.quiz.dto.response.ImageQuizMemberDetailResponse;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;

import java.io.IOException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageQuizService {

    private final ImageQuizRepository imageQuizRepository;
    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader;

    private static final String BUCKET_NAME = "kidbean.s3.ap-northeast-2.amazonaws.com";

    public ImageQuizMemberDetailResponse getImageQuizById(Long memberId, Long quizId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
        ImageQuiz imageQuiz = imageQuizRepository.findByQuizIdAndMember(quizId, member)
                .orElseThrow(RuntimeException::new);

        return ImageQuizMemberDetailResponse.from(imageQuiz);
    }

    @Transactional
    public void uploadImageQuiz(ImageQuizUploadRequest request, Long memberId, MultipartFile image) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);

        String folderName = "quiz" + "/" +  request.category();
        String uploadUrl = s3Uploader.upload(image, folderName);

        String generatedPath = uploadUrl.split("/" + BUCKET_NAME + "/" + folderName + "/")[1];

        ImageQuiz imageQuiz = request.toImageQuiz(member);
        imageQuiz.setImageInfo(
                ImageInfo.builder()
                        .imageUrl(uploadUrl)
                        .fileName(generatedPath)
                        .folderName("quiz" + "/" + request.category())
                        .build());

        imageQuizRepository.save(imageQuiz);
    }

    @Transactional
    public void updateImageQuiz(ImageQuizUpdateRequest request, Long memberId, Long quizId, MultipartFile image) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
        ImageQuiz imageQuiz = imageQuizRepository.findById(quizId)
                .orElseThrow(RuntimeException::new);

        // 이미지 수정이 되지 않는 것 default
        ImageInfo imageInfo = imageQuiz.getImageInfo();
        log.info("ImageName : " + image.getOriginalFilename());

        if (!image.getOriginalFilename().isEmpty()) {
            s3Uploader.deleteFile(imageQuiz.getImageInfo());

            String updateFolderName = "quiz" + "/" + request.category();
            String updateUrl = s3Uploader.upload(image, updateFolderName);
            String generatedPath = updateUrl.split("/" + BUCKET_NAME + "/" + updateFolderName + "/")[1];
            imageInfo = ImageInfo.builder()
                    .imageUrl(updateUrl)
                    .fileName(generatedPath)
                    .folderName("quiz" + "/" + request.category())
                    .build();
        }

        imageQuiz.update(request.title(), request.answer(), request.category(), imageInfo);
    }
}
