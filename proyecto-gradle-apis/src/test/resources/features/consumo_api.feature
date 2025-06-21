Feature: Obtener informacion del servicio EDOC

  @EmitirFacturas
  Scenario Outline: Emitir facturas en España 
    Given Generar Token de acceso con autenticacion basica
    When Emitir factura con el endpoint de emision y con variedad de datos "<NumFactura>"
    Examples:
      | NumFactura      |
      | 12345678        |
      | 12345679        |

  @ValidarEmisionConFechasIncorrectas
  Scenario Outline: Emitir facturas en España 
    Given Generar Token de acceso con autenticacion basica
    When Emitir factura con el endpoint de emision y con variedad de datos "<NumFactura>"
    Examples:
      | NumFactura |
      | 100349     |
      | 100350     |
      | 100351     |
      | 100352     |
      | 100353     |











