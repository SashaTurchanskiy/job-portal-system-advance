package dto.request;

import domain.CompanySize;
import domain.CompanyType;
import domain.IndustryType;
import dto.response.SocialLinkResponse;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequest {

    @NotBlank(message = "Company name is required")
    private String name;

    private String tagline;

    private String description;

    private String logoUrl;
    private String coverImageUrl;

    @Pattern(regexp = "^(https?://).*", message = "Invalid website URL")
    private String website;

    @Email(message = "Invalid email address")
    private String email;

    private String phone;

    @Min(value = 1800, message = "Founded year must be greater than or equal to 1800")
    @Max(value = 2100, message = "Founded year must be less than or equal to 2100")
    private Integer foundedYear;

    @NotNull(message = "Company size is required")
    private CompanySize companySize;

    @NotNull(message = "Company type is required")
    private CompanyType companyType;

    @NotNull(message = "Industry type is required")
    private IndustryType industryType;

    private String registrationNumber;

    private List<SocialLinkResponse> socialLinks;



}
