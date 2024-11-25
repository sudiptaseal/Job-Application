package com.embarkx.firstjobapp.company;

import com.embarkx.firstjobapp.company.dto.CompanyRequestDTO;
import com.embarkx.firstjobapp.company.dto.CompanyResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponseDTO>> getAllCompanies() {
        List<Company> company = companyService.getAllCompanies();
        List<CompanyResponseDTO> companyResponseDTOs = new ArrayList<>();
        if(company != null && company.size() > 0){
            company.stream().forEach(com ->{
                CompanyResponseDTO companyResponseDTO = new CompanyResponseDTO();
                companyResponseDTO.setId(com.getId());
                companyResponseDTO.setName(com.getName());
                companyResponseDTO.setDescription(com.getDescription());
                companyResponseDTO.setReviews(com.getReviews());
                companyResponseDTOs.add(companyResponseDTO);
            });
        }
        return new ResponseEntity<>(companyResponseDTOs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody CompanyRequestDTO companyRequestDTO){
        if(companyRequestDTO != null) {
            Company company = new Company();
            company.setName(companyRequestDTO.getName());
            company.setDescription(companyRequestDTO.getDescription());

            boolean companyUpdated = companyService.updateCompany(company,id);

            if(companyUpdated) {
                return new ResponseEntity<>("Company updated successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody CompanyRequestDTO companyRequestDTO) {
        if(companyRequestDTO != null){
            Company company = new Company();
            company.setName(companyRequestDTO.getName());
            company.setDescription(companyRequestDTO.getDescription());

            companyService.createCompany(company);
        }
        return new ResponseEntity<>("Company added successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        boolean companyDeleted = companyService.deleteCompanyById(id);
        if(companyDeleted){
            return  new ResponseEntity<>("Company deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> getCompany(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        if(company != null) {
            CompanyResponseDTO companyResponseDTO = new CompanyResponseDTO();
            companyResponseDTO.setId(company.getId());
            companyResponseDTO.setName(company.getName());
            companyResponseDTO.setDescription(company.getDescription());
            companyResponseDTO.setReviews(company.getReviews());

            return new ResponseEntity<>(companyResponseDTO,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
