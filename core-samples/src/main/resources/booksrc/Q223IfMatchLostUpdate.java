@PutMapping("/documents/{id}")
ResponseEntity<DocumentResponse> update(
        @PathVariable long id,
        @RequestHeader("If-Match") String ifMatch,
        @RequestBody UpdateDocumentRequest request) {
    Document updated = service.updateIfVersionMatches(id, ifMatch, request);
    return ResponseEntity.ok()
        .eTag(updated.etag())
        .body(DocumentResponse.from(updated));
}