<p align="center">
  <img src="https://pac4j.github.io/pac4j/img/logo-ratpack.png" width="300" />
</p>

This org.pac4j:ratpack-pac4j library is a mirror of the official Ratpack / pac4j module (io.ratpack:ratpack-pac4j) with newer versions of pac4j (as the Ratpack 1.x stream is stuck to pac4j v1.8.x).

| Pac4j         | Ratpack | New org.pac4j:ratpack-pac4j | Changes                                                                                                                   | Official io.ratpack:ratpack-pac4j |
|---------------|---------|-----------------------------|---------------------------------------------------------------------------------------------------------------------------|-----------------------------------|
| v1.8.x        | v1.4.6  | v1.4.6                      | No changes: both modules are identical                                                                                    | v1.4.6                            |
| v2.x (v2.1.0) | v1.5.0  | v2.0.0                      | The method signatures have changed: `CommonProfile` replaces `UserProfile` and `HttpAction` replaces `RequiresHttpAction` | it doesn't exist                  |
| v3.x (v3.3.0) | v1.5.0  | v3.0.0                      | Created a `RatpackSessionStore` and manually retrieved the `client_name`.                                                 | it doesn't exist                  |
| v5.x          | v1.9.0  | v4.x                        |                                                                                                                           | it doesn't exist                  |
| v6.x          | v1.9.0  | v5.x                        |                                                                                                                           | it doesn't exist                  |

See the [official documentation](https://ratpack.io/manual/1.9.0/pac4j.html#pac4j) and the [demo](https://github.com/pac4j/ratpack-pac4j-demo).
