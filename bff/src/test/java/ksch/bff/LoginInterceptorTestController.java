/*
 * Copyright 2022 KS-plus e.V.
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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginInterceptorTestController {

    @GetMapping("/login-interceptor/test.html")
    Object getHtmlPage() {
        return "<html><body>Hello</body></html>";
    }

    @GetMapping("/api/greeting")
    Object getGreeting() {
        return "Hello";
    }

    @GetMapping("/login-interceptor/logo.png")
    Object getStaticResource() {
        return "00100011110000111100000000011111111110001101000111010101";
    }
}
