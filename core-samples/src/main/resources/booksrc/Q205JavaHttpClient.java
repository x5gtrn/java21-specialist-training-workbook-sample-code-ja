// HttpClient の作成と再利用
HttpClient client = HttpClient.newBuilder()
    .version(HttpClient.Version.HTTP_2)  // HTTP/2 対応
    .connectTimeout(Duration.ofSeconds(10))
    .followRedirects(HttpClient.Redirect.NORMAL)
    .build();  // POINT: イミュータブル、スレッドセーフ → 再利用可能

// 同期リクエスト
HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("https://api.example.com/users/1"))
    .header("Accept", "application/json")
    .GET()
    .build();

HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
System.out.println(response.statusCode());
System.out.println(response.body());

// 非同期リクエスト
CompletableFuture<HttpResponse<String>> future = client.sendAsync(
    request, HttpResponse.BodyHandlers.ofString());

future.thenApply(HttpResponse::body)
      .thenAccept(System.out::println)
      .join();