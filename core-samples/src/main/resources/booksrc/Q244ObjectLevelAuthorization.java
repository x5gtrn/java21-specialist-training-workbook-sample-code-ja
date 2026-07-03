@GetMapping("/documents/{id}")
DocumentResponse get(@PathVariable long id) {
    Document document = repository.findById(id).orElseThrow();
    return DocumentResponse.from(document);
}
@@BLOCK@@
@PreAuthorize("@documentPolicy.canRead(#id, authentication)")
@GetMapping("/documents/{id}")
DocumentResponse get(@PathVariable long id) {
    return service.getDocument(id);
}
@@BLOCK@@
Optional<Document> findByIdAndOwnerId(long id, long ownerId);
@@BLOCK@@
@Transactional(readOnly = true)
public DocumentResponse get(long documentId, UserPrincipal user) {
    Document document = repository
        .findReadableDocument(documentId, user.id(), user.tenantId())
        .orElseThrow(() -> new NotFoundException());
    return DocumentResponse.from(document);
}