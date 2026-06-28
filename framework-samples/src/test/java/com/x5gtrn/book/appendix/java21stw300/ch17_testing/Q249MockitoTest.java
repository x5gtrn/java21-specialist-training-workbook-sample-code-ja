package com.x5gtrn.book.appendix.java21stw300.ch17_testing;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 問題249: Mockito のベストプラクティス。
 * 依存（リポジトリ）をモック化し、戻り値をスタブして対象のロジックだけを検証する。
 * 実行: ./gradlew :framework-samples:test
 */
class Q249MockitoTest {

    interface UserRepository {
        Optional<String> findNameById(long id);
    }

    static class UserService {
        private final UserRepository repository;

        UserService(UserRepository repository) {
            this.repository = repository;
        }

        String greet(long id) {
            return repository.findNameById(id).map(name -> "Hello, " + name).orElse("Unknown");
        }
    }

    @Test
    void greetsKnownUser() {
        UserRepository repository = mock(UserRepository.class);
        when(repository.findNameById(1L)).thenReturn(Optional.of("Alice"));

        UserService service = new UserService(repository);

        assertEquals("Hello, Alice", service.greet(1L));
        verify(repository, times(1)).findNameById(1L);
    }

    @Test
    void handlesUnknownUser() {
        UserRepository repository = mock(UserRepository.class);
        when(repository.findNameById(anyLong())).thenReturn(Optional.empty());

        assertEquals("Unknown", new UserService(repository).greet(99L));
    }
}
