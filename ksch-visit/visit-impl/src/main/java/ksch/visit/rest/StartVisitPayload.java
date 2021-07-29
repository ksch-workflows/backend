package ksch.visit.rest;

import ksch.visit.VisitType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Setter
@Getter
public class StartVisitPayload {

    @NotNull
    private VisitType type;
}
