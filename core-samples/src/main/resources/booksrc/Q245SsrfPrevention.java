URI uri = URI.create(inputUrl);
if (!List.of("https").contains(uri.getScheme())) {
    throw new BadRequestException("unsupported scheme");
}
if (!allowedHosts.contains(uri.getHost())) {
    throw new BadRequestException("host not allowed");
}