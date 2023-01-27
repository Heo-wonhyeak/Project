package hello.hellospring.res.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileUploadResponse {

    private String fileName;

    private String downloadUri;

    private Long size;
}
