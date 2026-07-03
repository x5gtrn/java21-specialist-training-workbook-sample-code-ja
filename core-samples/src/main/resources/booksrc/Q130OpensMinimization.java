module com.example.api {
    requires com.fasterxml.jackson.databind;

    opens com.example.api.dto to com.fasterxml.jackson.databind;
}
@@BLOCK@@
exports com.example.api.dto;
@@BLOCK@@
opens com.example.api.dto to com.fasterxml.jackson.databind;
@@BLOCK@@
module com.example.app {
    requires com.fasterxml.jackson.databind;
    opens com.example.app.dto to com.fasterxml.jackson.databind;
}
@@BLOCK@@
open module com.example.app {
    requires com.fasterxml.jackson.databind;
}
@@BLOCK@@
module com.example.app {
    requires com.fasterxml.jackson.databind;
    opens com.example.app.dto to com.fasterxml.jackson.databind;
}
@@BLOCK@@
// 公開 API に DTO が出るなら exports を検討
public OrderDto getOrder(String id);