document.addEventListener('DOMContentLoaded', () => {

    // --- Selección de paciente ---
    document.addEventListener('click', (e) => {
        if (e.target.classList.contains('btn-seleccionar-paciente')) {
            const id = e.target.dataset.id;
            const nombre = e.target.dataset.nombre;
            document.getElementById('pacienteId').value = id;
            document.getElementById('pacienteNombre').value = nombre;
            bootstrap.Modal.getInstance(document.getElementById('modalPacientes')).hide();
        }
    });

    // --- Selección de médico ---
    document.addEventListener('click', (e) => {
        if (e.target.classList.contains('btn-seleccionar-medico')) {
            const id = e.target.dataset.id;
            const nombre = e.target.dataset.nombre;
            document.getElementById('medicoId').value = id;
            document.getElementById('medicoNombre').value = nombre;
            bootstrap.Modal.getInstance(document.getElementById('modalMedicos')).hide();
        }
    });

    // --- Buscar pacientes por AJAX ---
    const btnBuscarPaciente = document.getElementById('btnBuscarPaciente');
    if (btnBuscarPaciente) {
        btnBuscarPaciente.addEventListener('click', () => {
            const texto = document.getElementById('buscarPacienteInput').value.trim();
            fetch(`/recepcionista/cita/buscarPacientes?texto=${encodeURIComponent(texto)}`)
                .then(res => res.json())
                .then(pacientes => actualizarTablaPacientes(pacientes))
                .catch(err => console.error('Error al buscar pacientes:', err));
        });
    }

    // --- Buscar médicos por AJAX ---
    const btnBuscarMedico = document.getElementById('btnBuscarMedico');
    if (btnBuscarMedico) {
        btnBuscarMedico.addEventListener('click', () => {
            const texto = document.getElementById('buscarMedicoInput').value.trim();
            fetch(`/recepcionista/cita/buscarMedicos?texto=${encodeURIComponent(texto)}`)
                .then(res => res.json())
                .then(medicos => actualizarTablaMedicos(medicos))
                .catch(err => console.error('Error al buscar médicos:', err));
        });
    }

    // === Cargar todos los pacientes cuando se abre el modal ===
    const modalPacientes = document.getElementById('modalPacientes');
    if (modalPacientes) {
        modalPacientes.addEventListener('shown.bs.modal', () => {
            fetch(`/recepcionista/cita/buscarPacientes`)
                .then(res => res.json())
                .then(pacientes => actualizarTablaPacientes(pacientes))
                .catch(err => console.error('Error al cargar pacientes:', err));
        });
    }

    // === Cargar todos los médicos cuando se abre el modal ===
    const modalMedicos = document.getElementById('modalMedicos');
    if (modalMedicos) {
        modalMedicos.addEventListener('shown.bs.modal', () => {
            fetch(`/recepcionista/cita/buscarMedicos`)
                .then(res => res.json())
                .then(medicos => actualizarTablaMedicos(medicos))
                .catch(err => console.error('Error al cargar médicos:', err));
        });
    }
});

// === Funciones auxiliares ===

// Renderizar tabla de pacientes
function actualizarTablaPacientes(pacientes) {
    const tbody = document.querySelector('#tablaPacientes tbody');
    tbody.innerHTML = '';

    if (!pacientes || pacientes.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4" class="text-center text-muted">No se encontraron pacientes</td></tr>`;
        return;
    }

    pacientes.forEach(p => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${p.nombre}</td>
            <td>${p.apellido}</td>
            <td>${p.dni}</td>
            <td>
                <button type="button" class="btn btn-success btn-seleccionar-paciente"
                        data-id="${p.idPaciente}"
                        data-nombre="${p.nombre} ${p.apellido}">
                    Seleccionar
                </button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Renderizar tabla de médicos
function actualizarTablaMedicos(medicos) {
    const tbody = document.querySelector('#tablaMedicos tbody');
    tbody.innerHTML = '';

    if (!medicos || medicos.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4" class="text-center text-muted">No se encontraron médicos</td></tr>`;
        return;
    }

    medicos.forEach(m => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${m.nombres}</td>
            <td>${m.apellidos}</td>
            <td>${m.especialidad ? m.especialidad.nombreEspecialidad : 'Sin especialidad'}</td>
            <td>
                <button type="button" class="btn btn-success btn-seleccionar-medico"
                        data-id="${m.idMedico}"
                        data-nombre="${m.nombres} ${m.apellidos}">
                    Seleccionar
                </button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// === Mostrar horarios en el modal ===
function mostrarHorarios(idMedico, fecha) {
    if (!idMedico || !fecha) {
        Swal.fire("Error", "Debes seleccionar un médico y una fecha", "error");
        return;
    }

    fetch(`/recepcionista/cita/horarios?idMedico=${idMedico}&fecha=${fecha}`)
        .then(response => response.text())
        .then(html => {
            document.getElementById('modalHorariosContainer').innerHTML = html;
            const modal = new bootstrap.Modal(document.getElementById('modalHorarios'));
            modal.show();
        })
        .catch(err => console.error('Error al cargar horarios:', err));
}
