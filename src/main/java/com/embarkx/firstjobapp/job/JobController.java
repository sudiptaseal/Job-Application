package com.embarkx.firstjobapp.job;

import com.embarkx.firstjobapp.job.dto.JobRequestDTO;
import com.embarkx.firstjobapp.job.dto.JobResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobResponseDTO>> findAll() {
        List<Job> jobs = jobService.findAll();
        List<JobResponseDTO> jobResponseDTOs = new ArrayList<>();
        if(jobs != null && jobs.size() > 0){
            jobs.stream().forEach(job -> {
                JobResponseDTO jobResponseDTO = new JobResponseDTO();
                jobResponseDTO.setId(job.getId());
                jobResponseDTO.setTitle(job.getTitle());
                jobResponseDTO.setDescription(job.getDescription());
                jobResponseDTO.setMinSalary(job.getMinSalary());
                jobResponseDTO.setMaxSalary(job.getMaxSalary());
                jobResponseDTO.setLocation(job.getLocation());
                jobResponseDTO.setCompany(job.getCompany());

                jobResponseDTOs.add(jobResponseDTO);
            });
        }
        return ResponseEntity.ok(jobResponseDTOs);
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody JobRequestDTO jobRequestDto) {
        if(jobRequestDto != null){
            Job job =new Job();
            job.setTitle(jobRequestDto.getTitle());
            job.setDescription(jobRequestDto.getDescription());
            job.setMinSalary(jobRequestDto.getMinSalary());
            job.setMaxSalary(jobRequestDto.getMaxSalary());
            job.setLocation(jobRequestDto.getLocation());
            job.setCompany(jobRequestDto.getCompany());

            jobService.creatJob(job);
        }
        return new ResponseEntity<>("Job added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDTO> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        if(job != null) {
            JobResponseDTO jobResponseDTO = new JobResponseDTO();
            jobResponseDTO.setId(job.getId());
            jobResponseDTO.setTitle(job.getTitle());
            jobResponseDTO.setDescription(job.getDescription());
            jobResponseDTO.setMinSalary(job.getMinSalary());
            jobResponseDTO.setMaxSalary(job.getMaxSalary());
            jobResponseDTO.setLocation(job.getLocation());
            jobResponseDTO.setCompany(job.getCompany());

            return new ResponseEntity<>(jobResponseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        Boolean deleted = jobService.deleteJobById(id);
        if(deleted)
            return new ResponseEntity<>("Job deleted successfully",HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //@PutMapping("/jobs/{id}")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody JobRequestDTO jobRequestDTO) {
        if(jobRequestDTO != null) {
            Job updatedJob = new Job();
            updatedJob.setTitle(jobRequestDTO.getTitle());
            updatedJob.setDescription(jobRequestDTO.getDescription());
            updatedJob.setMinSalary(jobRequestDTO.getMinSalary());
            updatedJob.setMaxSalary(jobRequestDTO.getMaxSalary());
            updatedJob.setLocation(jobRequestDTO.getLocation());
            updatedJob.setCompany(jobRequestDTO.getCompany());

            boolean updated = jobService.updateJob(id, updatedJob);
            if(updated)
                return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
