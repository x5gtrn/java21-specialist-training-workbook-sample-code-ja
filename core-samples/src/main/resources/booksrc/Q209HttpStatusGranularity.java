@ExceptionHandler(MethodArgumentNotValidException.class)
ResponseEntity<ProblemDetail> validation(MethodArgumentNotValidException ex) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problem.setTitle("Invalid request");
    return ResponseEntity.badRequest().body(problem);
}