@PostMapping("/reports")
ResponseEntity<JobResponse> create(@RequestBody ReportRequest request) {
    Job job = reportService.enqueue(request);
    URI location = URI.create("/reports/jobs/" + job.id());
    return ResponseEntity.accepted()
        .location(location)
        .body(JobResponse.from(job));
}