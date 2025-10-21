document.addEventListener("DOMContentLoaded", () => {

  // === Alertas con SweetAlert ===
  if (success) Swal.fire("Éxito", success, "success");
  if (error) Swal.fire("Error", error, "error");

  if (abrirModal) {
    new bootstrap.Modal(document.getElementById('modalMedico')).show();
  }

  // === Renderizar médicos ===
  function renderMedicos(data) {
    const tbody = document.getElementById("cuerpoMedicos");
    tbody.innerHTML = "";

    if (!data || data.length === 0) {
      tbody.innerHTML = "<tr><td colspan='4' class='text-center'>No se encontraron médicos</td></tr>";
      return;
    }

    data.forEach(medico => {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${medico.nombres}</td>
        <td>${medico.apellidos}</td>
        <td>${medico.especialidad ? medico.especialidad.nombreEspecialidad : ''}</td>
        <td>
          <button class="btn btn-sm btn-primary seleccionarMedico"
                  data-id="${medico.idMedico}"
                  data-dni="${medico.dni}"
                  data-nombre="${medico.nombres}"
                  data-apellidos="${medico.apellidos}"
                  data-especialidad="${medico.especialidad ? medico.especialidad.nombreEspecialidad : ''}">
            Seleccionar
          </button>
        </td>
      `;
      tbody.appendChild(tr);
    });
  }

  // === Buscar médicos ===
  function buscarMedico() {
    const nombre = document.getElementById("buscadorMedico").value.trim();
    const url = nombre ? `/medico/buscarJson?nombre=${encodeURIComponent(nombre)}` : `/medico/buscarJson`;

    fetch(url)
      .then(res => res.json())
      .then(renderMedicos)
      .catch(err => console.error("Error al buscar médicos:", err));
  }

  // === Eventos ===
  const cuerpoMedicos = document.getElementById("cuerpoMedicos");
  const btnBuscarMedico = document.getElementById("btnBuscarMedico");
  const buscadorMedico = document.getElementById("buscadorMedico");

  // Delegar evento para botón "Seleccionar"
  cuerpoMedicos.addEventListener("click", function (e) {
    if (e.target && e.target.classList.contains("seleccionarMedico")) {
      const btn = e.target;
      const idMedico = btn.dataset.id;

      document.getElementById("medicoId").value = idMedico;
      document.getElementById("medicoDni").value = btn.dataset.dni;
      document.getElementById("medicoNombres").value = btn.dataset.nombre;
      document.getElementById("medicoApellidos").value = btn.dataset.apellidos;
      document.getElementById("medicoEspecialidad").value = btn.dataset.especialidad;

      bootstrap.Modal.getInstance(document.getElementById('modalMedico')).hide();

      // Redirigir para cargar horarios
      window.location.href = `/gestionarHorarios?idMedico=${idMedico}`;
    }
  });

  // Botón buscar
  btnBuscarMedico.addEventListener("click", e => {
    e.preventDefault();
    buscarMedico();
  });

  // Buscar con Enter
  buscadorMedico.addEventListener("keypress", e => {
    if (e.key === "Enter") {
      e.preventDefault();
      buscarMedico();
    }
  });

  // Listar médicos al abrir el modal
  document.getElementById('modalMedico').addEventListener('shown.bs.modal', buscarMedico);

});
