import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class UserNotFoundException: ResponseStatusException(HttpStatus.BAD_REQUEST, "User not fond")