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
package ksch.visit.rest;

import ksch.commons.http.error.NotFoundException;
import ksch.visit.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;
import java.util.function.Supplier;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping("/patients/{patientId}/visits")
    public VisitResource startVisit(
            @PathVariable("patientId") UUID patientId,
            @RequestBody @Valid StartVisitPayload payload
    ) {
        var visit = visitService.startVisit(patientId, payload.getType());
        return VisitResource.from(visit);
    }

    @GetMapping("/patients/{patientId}/visits/{visitId}")
    public VisitResource getVisit(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("visitId") UUID visitId
    ) {
        var visit = visitService.getVisit(patientId, visitId).orElseThrow(visitNotFoundException(patientId, visitId));
        return VisitResource.from(visit);
    }

    private Supplier<NotFoundException> visitNotFoundException(UUID patientId, UUID visitId) {
        return () -> new NotFoundException(
                String.format("Could not find visit with ID '%s' for patient '%s'.", visitId, patientId
                )
        );
    }
}
