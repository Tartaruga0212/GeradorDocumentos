package org.example.sistemageradordeboleto;

import java.time.LocalDate;
import java.util.List;

public record ContratoRequest(
        String numeroContrato,
        LocalDate dataAssinatura,
        String parteA,
        String parteB,
        String objeto,
        List<String> clausulas,
        String prazo
) {}