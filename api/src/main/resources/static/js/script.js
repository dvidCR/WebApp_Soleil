/**
 * A la hora de cerrar sesion, limpia la cache y te lleva a la página de inicio de sesion
 */
function logout() {
    localStorage.clear();
    const homeUrl = document.body.dataset.homeUrl;
    window.location.href = homeUrl;
}

/**
 * Metodo para fichar tu hora de entrada y salida
 */
async function fichar() {
  try {
    const response = await fetch('/ficharVista', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'same-origin'
    });
    const mensaje = await response.text();
    const mensajeElem = document.getElementById('mensajeFichaje');
    mensajeElem.textContent = mensaje;
    mensajeElem.style.color = mensaje.includes('correctamente') ? 'green' : 'red';
  } catch (error) {
    console.error('Error al fichar:', error);
  }
}
  
  /**
   * Hace las opciones crear, actualizar y borrar del formulario del formulario gasto en contabilidad.html
   */
  function mostrarFormGasto(accion) {
    const form = document.getElementById('formGasto');
    form.style.display = 'block';
    if (accion === 'add') {
      form.action = '/gasto/add';
      form.id_gasto.value = '';
      form.reset();
    } else if (accion === 'update') {
      form.action = '/gasto/update';
    } else if (accion === 'delete') {
      form.action = '/gasto/delete';
      form.reset();
    }
  }
  
  /**
   * Es el codigo que autorrellena los campos con los datos existentes en configurarUsuarios.html 
   */
  function cargarEmpleado(dni) {
      const emp = empleados.find(e => e.dni === dni);
      if (emp) {
          document.querySelectorAll('[data-actualizar]').forEach(el => {
              const field = el.getAttribute('data-actualizar');
              if (el.tagName === "SELECT") {
                  el.value = emp[field] || "";
              } else {
                  el.value = emp[field] || "";
              }
          });
          document.querySelectorAll('[data-borrar]').forEach(el => {
              const field = el.getAttribute('data-borrar');
              el.value = emp[field] || "";
          });

		  const formActualizar = document.querySelector('#actualizarUsuario form');
		  if (formActualizar) {
		      formActualizar.action = '/configurarUsuarios/' + emp.dni;
		  }
		  const formBorrar = document.querySelector('#borrarUsuario form');
		  if (formBorrar) {
		      formBorrar.action = '/configurarUsuarios/' + emp.dni;
		  }

          document.getElementById('borrar-btn').setAttribute('onclick', `confirmarBorrado('${emp.dni}')`);
      }
  }
  
  /**
   * Metodo para cambiar el dni del empleado
   */
  function cambiarDniEmpleado() {
      const select = document.querySelector('#actualizarUsuario select');
      const dniAntiguo = select.value;
      const dniNuevo = document.getElementById('dni-input-empleado').value;

      if (!dniAntiguo || !dniNuevo) {
          alert('Por favor, seleccione un empleado y escriba el nuevo DNI.');
          return;
      }

      const form = document.createElement('form');
      form.method = 'post';
      form.action = `/configurarUsuarios/actualizarEmpleadoDNI/${dniAntiguo}`;

      form.innerHTML = `
          <input type="hidden" name="_method" value="put">
          <input type="hidden" name="nuevoDni" value="${dniNuevo}">
      `;

      document.body.appendChild(form);
      form.submit();
  }

  /**
    * Metodo para cambiar el dni del paciente
    */
  function cambiarDniPaciente() {
      const select = document.querySelector('#actualizarPaciente select');
      const dniAntiguo = select.value;
      const dniNuevo = document.getElementById('dni-input-paciente').value;

      if (!dniAntiguo || !dniNuevo) {
          alert('Por favor, seleccione un paciente y escriba el nuevo DNI.');
          return;
      }

      const form = document.createElement('form');
      form.method = 'post';
      form.action = `/configurarPacientes/actualizarPacienteDNI/${dniAntiguo}`;

      form.innerHTML = `
          <input type="hidden" name="_method" value="put">
          <input type="hidden" name="nuevoDni" value="${dniNuevo}">
      `;

      document.body.appendChild(form);
      form.submit();
  }
  
  /**
   * Hace las opciones crear, actualizar y borrar del formulario del formulario servicio en contabilidad.html
   */
  function mostrarFormServicio(accion) {
      const form = document.getElementById("formServicio");
      const selector = document.getElementById("servicioSelector");

      form.style.display = "block";

      if (accion === "update" || accion === "delete") {
          selector.style.display = "block";
          form.action = "/servicio/" + accion;
      } else {
          selector.style.display = "none";
          form.action = "/servicio/add";
      }

      document.getElementById("btnSubmit").textContent =
          accion === "add" ? "Añadir" : accion === "update" ? "Actualizar" : "Borrar";

      if (accion === "delete") {
          ponerCamposReadonly(true);
      } else {
          ponerCamposReadonly(false);
      }

      if (accion === "delete") {
          form.onsubmit = function(e) {
              if (!confirm('¿Estás seguro de que quieres borrar este gasto?')) {
                  e.preventDefault();
              } else if (!confirm('Esta acción es irreversible. ¿Continuar?')) {
                  e.preventDefault();
              }
          };
      } else {
          form.onsubmit = null;
      }
  }

  /** 
   * Pone los campos del formulario de servicios en modo readonly en contabilidad.html
   */
  function ponerCamposReadonly(readonly) {
      const camposId = [
          "dni_empleado",
          "dni_paciente",
          "id_tratamiento",
          "fecha_cita",
          "modo_pago",
          "tarifa",
          "concepto",
          "num_sesiones"
      ];

      camposId.forEach(id => {
          const elem = document.getElementById(id);
          if (!elem) return;

          if (readonly) {
              elem.setAttribute("disabled", "disabled");
              elem.style.backgroundColor = "#eee";
          } else {
              elem.removeAttribute("disabled");
              elem.style.backgroundColor = "";
          }
      });
  }
  
  /** 
     * Pone los campos select del formulario de servicios en modo readonly en contabilidad.html
     */
  function convertirSelectsAInputsReadonly() {
      const campos = ["dni_empleado", "dni_paciente", "id_tratamiento"];
      campos.forEach(id => {
          const select = document.getElementById(id);
          if (!select) return;
          if (select.tagName.toLowerCase() === "select") {
              const input = document.createElement("input");
              input.type = "text";
              input.id = id;
              input.name = id;
              input.readOnly = true;
              input.value = select.options[select.selectedIndex]?.text || "";
              select.parentNode.replaceChild(input, select);
          }
      });
  }

  function restaurarSelects() {
      location.reload();
  }

  /**
   * Autorrellena los campos del formulario a la hora de actualizar y borrar en contabilidad.html
   */
  function rellenarCamposServicio(id) {
      const serviciosMap = window.serviciosMap || [];
      if (!id || serviciosMap.length === 0) return;

      const servicio = serviciosMap.find(s => s.id_servicio == id);
      if (!servicio) return;

      document.getElementById('id_servicio').value = servicio.id_servicio;
      document.getElementById('fecha_cita').value = servicio.fecha_cita?.substring(0, 10) || '';

      const dniEmpleadoElem = document.getElementById('dni_empleado');
      if (dniEmpleadoElem.tagName.toLowerCase() === 'input') {
          dniEmpleadoElem.value = servicio.dni_empleado ? `${servicio.dni_empleado.dni} - ${servicio.dni_empleado.nombre} ${servicio.dni_empleado.apellidos}` : '';
      } else {
          dniEmpleadoElem.value = servicio.dni_empleado?.dni || '';
      }

      const dniPacienteElem = document.getElementById('dni_paciente');
      if (dniPacienteElem.tagName.toLowerCase() === 'input') {
          dniPacienteElem.value = servicio.dni_paciente ? `${servicio.dni_paciente.dni} - ${servicio.dni_paciente.nombre} ${servicio.dni_paciente.apellidos}` : '';
      } else {
          dniPacienteElem.value = servicio.dni_paciente?.dni || '';
      }

      document.getElementById('id_tratamiento').value = servicio.id_tratamiento?.id_tratamiento || '';
      document.getElementById('modo_pago').value = servicio.modo_pago || '';
      document.getElementById('tarifa').value = servicio.tarifa || '';
      document.getElementById('concepto').value = servicio.concepto || '';
      document.getElementById('num_sesiones').value = servicio.num_sesiones || '';
  }

  /**
   * Vuelve visible el formulario seleccionado
   */
  function mostrarSeccion(id) {
      const secciones = document.querySelectorAll('.formulario-section');
      secciones.forEach(seccion => seccion.classList.remove('visible'));
      
      const seleccionada = document.getElementById(id);
      if (seleccionada) {
          seleccionada.classList.add('visible');
      }
  }
  
  function mostrarFormGasto(accion) {
      const form = document.getElementById('formGasto');
      const selector = document.getElementById('gastoSelector');
      const btn = document.getElementById('btnGastoSubmit');
      
      form.style.display = 'block';
      form.reset();

      if (accion === 'add') {
          form.action = '/gasto/add';
          selector.style.display = 'none';
          ponerCamposGastoReadonly(false);
          btn.textContent = 'Añadir';
      } else {
          selector.style.display = 'block';
          form.action = '/gasto/' + accion;
          btn.textContent = accion === 'update' ? 'Actualizar' : 'Borrar';

          ponerCamposGastoReadonly(accion === 'delete');

          form.onsubmit = accion === 'delete' ? function(e) {
              if (!confirm('¿Estás seguro de que quieres borrar este gasto?')) {
                  e.preventDefault();
              } else if (!confirm('Esta acción es irreversible. ¿Continuar?')) {
                  e.preventDefault();
              }
          } : null;
      }
  }

  function rellenarCamposGasto(id) {
      const gastos = window.gastosMap || [];
      const gasto = gastos.find(g => g.id_gasto == id);
      if (!gasto) return;

      document.getElementById('id_gasto').value = gasto.id_gasto;
      document.getElementById('cantidad').value = gasto.cantidad;
      document.getElementById('motivo').value = gasto.motivo;
      document.getElementById('proveedor').value = gasto.proveedor;
      document.getElementById('fecha').value = gasto.fecha?.substring(0, 10) || '';
  }
	
  function ponerCamposGastoReadonly(readonly) {
      const campos = ["cantidad", "motivo", "proveedor", "fecha"];
      campos.forEach(id => {
          const elem = document.getElementById(id);
          if (!elem) return;

          if (readonly) {
              elem.setAttribute("readonly", "readonly");
              elem.style.backgroundColor = "#eee";
          } else {
              elem.removeAttribute("readonly");
              elem.style.backgroundColor = "";
          }
      });
  }
  
  function cargarPaciente(dni, modo) {
        const paciente = pacientes.find(p => p.dni === dni);
        if (!paciente) return;

        if (modo === 'actualizar') {
            // Autorrellenar campos de actualización
            document.querySelectorAll('#actualizarPaciente [data-actualizar]').forEach(el => {
                const campo = el.getAttribute('data-actualizar');
                el.value = paciente[campo] || '';
            });

            const formActualizar = document.querySelector('#actualizarPaciente form');
            if (formActualizar) {
                formActualizar.action = `/configurarPacientes/${dni}`;
            }
        } else if (modo === 'borrar') {
            // Autorrellenar campos de borrado (readonly)
            document.querySelectorAll('#borrarPaciente [data-borrar]').forEach(el => {
                const campo = el.getAttribute('data-borrar');
                el.value = paciente[campo] || '';
            });

            const formBorrar = document.querySelector('#borrarPaciente form');
            if (formBorrar) {
                formBorrar.action = `/configurarPacientes/${dni}`;
            }
        }
    }
	
	function cargarTratamiento(id, modo) {
	    const tratamiento = tratamientos.find(t => t.id_tratamiento == id);
	    if (!tratamiento) return;
	
	    if (modo === 'actualizar') {
	        document.querySelectorAll('#actualizarTratamiento [data-actualizar]').forEach(el => {
	            const campo = el.getAttribute('data-actualizar');
	            if (campo === 'dni_paciente') {
	                if (tratamiento.dni_paciente != null) {
	                    el.value = tratamiento.dni_paciente.dni;
	                } else {
	                    el.value = '';
	                }
	            } else {
	                el.value = tratamiento[campo] || '';
	            }
	        });
	        const formActualizar = document.querySelector('#actualizarTratamiento form');
	        if (formActualizar) {
	            formActualizar.action = `/configurarTratamientos/${id}`;
	        }
	    } else if (modo === 'borrar') {
	        document.querySelectorAll('#borrarTratamiento [data-borrar]').forEach(el => {
	            const campo = el.getAttribute('data-borrar');
	            if (campo === 'dni_paciente') {
	                el.value = tratamiento.dni_paciente != null ? tratamiento.dni_paciente.dni : '';
	            } else {
	                el.value = tratamiento[campo] || '';
	            }
	        });
	        const formBorrar = document.querySelector('#borrarTratamiento form');
	        if (formBorrar) {
	            formBorrar.action = `/configurarTratamientos/${id}`;
	        }
	    }
	}
	
	// Función para mostrar y ocultar secciones
	function mostrarSeccion(id) {
	    document.querySelectorAll('.formulario-section').forEach(sec => {
	        sec.classList.remove('visible');
	    });
	    document.getElementById(id).classList.add('visible');
	}

	document.addEventListener("DOMContentLoaded", function () {
	  const empleadoFilter = document.getElementById("empleadoFilter");
	  const fechaFilter = document.getElementById("fechaFilter");
	  const tablaBody = document.querySelector("#tablaFichajes tbody");

	  function renderTabla() {
	    const dniSeleccionado = empleadoFilter.value;
	    const fechaSeleccionada = fechaFilter.value;

	    Array.from(tablaBody.rows).forEach(row => {
	      const dni = row.cells[1].textContent.trim(); // Columna DNI
	      const fecha = row.cells[4].textContent.trim(); // Columna Fecha (índice 4 porque la primera es ID)

	      const coincideEmpleado = !dniSeleccionado || dni === dniSeleccionado;
	      const coincideFecha = !fechaSeleccionada || fecha.startsWith(fechaSeleccionada);

	      row.style.display = (coincideEmpleado && coincideFecha) ? "" : "none";
	    });
	  }

	  empleadoFilter.addEventListener("change", renderTabla);
	  fechaFilter.addEventListener("input", renderTabla);

	  renderTabla();
	});
	
  function cerrarForm(id) {
    document.getElementById(id).style.display = 'none';
  }