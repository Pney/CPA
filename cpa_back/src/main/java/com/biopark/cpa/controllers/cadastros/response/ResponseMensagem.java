package com.biopark.cpa.controllers.cadastros.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ResponseMensagem {
    private String status;
    private String mensagem;
}
