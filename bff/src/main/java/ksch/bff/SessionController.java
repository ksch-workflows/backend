/*
 * Copyright 2021 KS-plus e.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ksch.bff;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/bff/session")
    ResponseEntity<?> handleAuthorizationCallback(@RequestParam String returnUrl) {
        // The Login interceptor sends the browser to the authorization server, if necessary.
        // This request will only be processed if there is a valid session. Hence, the browser
        // will actually get an initialized session after calling this endpoint, even though there
        // is no business logic in here.
        return ResponseEntity.status(304).header("Location", returnUrl).build();
    }
}
