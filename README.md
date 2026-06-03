# AutoRescate 24/7

Proyecto Java para Eclipse con patron MVC y estructuras propias basadas en arreglos, colas, pilas y cola de prioridad.

## Organizacion MVC

- Modelo: autorescate.model, autorescate.model.entidades, autorescate.model.funciones, autorescate.model.programa.
- Controlador: autorescate.controller.AutoRescateController.
- Vista: autorescate.view.AutoRescateFrame.
- Entrada: autorescate.Main.

## Estructuras Propias

- ArregloDinamico<T>: almacena clientes, tecnicos, unidades e historico.
- Cola<T>: solicitudes ordinarias por orden de llegada.
- ColaPrioridad<T>: solicitudes criticas por mayor prioridad.
- Pila<T>: kits en revision y movimientos recientes.

## Requisitos Funcionales Base

1. Registrar clientes, tecnicos y unidades.
2. Crear solicitudes ordinarias y criticas asociadas a clientes.
   La vista permite escoger incidentes frecuentes como vehiculo varado,
   llanta pinchada, choque, bateria descargada, falta de combustible,
   fallas mecanicas/electricas, bloqueo de via, personas en riesgo u otro.
3. Asignar un tecnico y una unidad disponibles a una solicitud.
4. Impedir que tecnicos ocupados o unidades no disponibles sean asignados.
5. Cambiar estado de unidades entre disponible, asignada, mantenimiento y fuera de servicio.
6. Cerrar servicios solo cuando tengan recursos asignados.
7. Consultar pendientes ordinarios, criticos, historico y recursos.
8. Registrar kits en revision y retirar el ultimo ingresado.
9. Registrar movimientos operativos y revertir operaciones recientes.
10. Exportar CSV de casos cerrados durante el dia.

## Base Para Diagrama De Clases

- Main crea AutoRescateController y AutoRescateFrame.
- AutoRescateFrame usa AutoRescateController.
- AutoRescateController usa SistemaAutoRescate.
- SistemaAutoRescate contiene ArregloDinamico<Cliente>, ArregloDinamico<Tecnico>, ArregloDinamico<UnidadServicio>, ArregloDinamico<SolicitudServicio>, Cola<SolicitudServicio>, ColaPrioridad<SolicitudServicio>, Pila<KitAtencion> y Pila<MovimientoOperacion>.
- SolicitudServicio se asocia con Cliente, Tecnico y UnidadServicio.
- MovimientoOperacion puede referenciar SolicitudServicio, Tecnico y UnidadServicio para revertir asignaciones, cierres y cambios de unidad.
