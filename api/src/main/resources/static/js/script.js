function logout() {
    localStorage.clear();
    const homeUrl = document.body.dataset.homeUrl;
    window.location.href = homeUrl;
}

async function fichar() {
    const response = await fetch('/ficharVista', {
        method: 'POST'
    });

    const result = await response.text();
    alert(result);
	console.log(result);
}

function mostrarFormServicio(accion) {
    const form = document.getElementById('formServicio');
    form.style.display = 'block';
    if (accion === 'add') {
      form.action = '/servicio/add';
      form.id_servicio.value = '';
      form.reset();
    } else if (accion === 'update') {
      form.action = '/servicio/update';
    } else if (accion === 'delete') {
      form.action = '/servicio/delete';
      form.reset();
    }
  }
  
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

          // Establece el action del formulario correctamente
		  const formActualizar = document.querySelector('#actualizarUsuario form');
		  if (formActualizar) {
		      formActualizar.action = '/configurarUsuarios/' + emp.dni;
		  }
		  const formBorrar = document.querySelector('#borrarUsuario form');
		  if (formBorrar) {
		      formBorrar.action = '/configurarUsuarios/' + emp.dni;
		  }

          // Botón de confirmación de borrado
          document.getElementById('borrar-btn').setAttribute('onclick', `confirmarBorrado('${emp.dni}')`);
      }
  }
  
  function cambiarDni() {
      const select = document.querySelector('#actualizarUsuario select');
      const dniAntiguo = select.value;
      const dniNuevo = document.getElementById('dni-input').value;

      if (!dniAntiguo || !dniNuevo) {
          console.log('DNI antiguo o nuevo no especificado');
          return;
      }

      const form = document.createElement('form');
      form.method = 'post';
      form.action = `/configurarUsuarios/actualizarDNI/${dniAntiguo}`;

      const methodInput = document.createElement('input');
      methodInput.type = 'hidden';
      methodInput.name = '_method';
      methodInput.value = 'put';
      form.appendChild(methodInput);

      const nuevoDniInput = document.createElement('input');
      nuevoDniInput.type = 'hidden';
      nuevoDniInput.name = 'nuevoDni';
      nuevoDniInput.value = dniNuevo;
      form.appendChild(nuevoDniInput);

      document.body.appendChild(form);
      form.submit();
  }
  
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
          // En vez de convertir selects a inputs, solo deshabilitar selects e inputs
          ponerCamposReadonly(true);
      } else {
          ponerCamposReadonly(false);
          // Si hubo cambio previo, recarga no es necesaria
          // restaurarSelects();
      }
  }

  // Cambia ponerInputsReadonly por esta que abarca selects también
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

  function mostrarSeccion(id) {
      const secciones = document.querySelectorAll('.formulario-section');
      secciones.forEach(seccion => seccion.classList.remove('visible'));
      
      const seleccionada = document.getElementById(id);
      if (seleccionada) {
          seleccionada.classList.add('visible');
      }
  }

  function cerrarForm(id) {
    document.getElementById(id).style.display = 'none';
  }