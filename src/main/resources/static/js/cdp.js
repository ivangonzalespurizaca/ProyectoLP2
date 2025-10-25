function buscarCitas() {
    const texto = document.getElementById('textoCita').value.trim();

    fetch(`/cajero/comprobanteDePago/buscar?texto=${encodeURIComponent(texto)}`)
        .then(res => res.json())
        .then(citas => actualizarTablaCitas(citas))
        .catch(err => console.error('Error al buscar citas:', err));
}

function actualizarTablaCitas(citas) {
    const tbody = document.querySelector('#tablaCitas tbody');
    tbody.innerHTML = '';

    if (!citas || citas.length === 0) {
        tbody.innerHTML = `<tr><td colspan="7" class="text-center text-muted">No se encontraron citas</td></tr>`;
        return;
    }

    citas.forEach(c => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${c.idCita}</td>
            <td>${c.paciente.nombre} ${c.paciente.apellido}</td>
            <td>${c.fecha}</td>
            <td>${c.hora}</td>
            <td>${c.medico.nombres} ${c.medico.apellidos}</td>
            <td>${c.motivo}</td>
            <td>
                <button type="button" class="btn btn-success btn-seleccionar-cita"
                        data-id="${c.idCita}"
                        data-paciente="${c.paciente.nombre} ${c.paciente.apellido}">
                    Seleccionar
                </button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

document.querySelector('#tablaCitas tbody').addEventListener('click', (e) => {
    if (e.target.classList.contains('btn-seleccionar-cita')) {
        const id = e.target.dataset.id;

        // Asignar al input oculto y al campo visible
        document.getElementById('idCita').value = id;
        document.getElementById('citaSeleccionada').value = `Cita #${id}`;

        // También puedes rellenar los demás campos con info de la cita
        const tr = e.target.closest('tr');
        document.getElementById('pacienteInfo').value = tr.children[1].textContent;
        document.getElementById('fechaInfo').value = tr.children[2].textContent;
        document.getElementById('horaInfo').value = tr.children[3].textContent;
        document.getElementById('medicoInfo').value = tr.children[4].textContent;
        document.getElementById('motivoInfo').value = tr.children[5].textContent;

        bootstrap.Modal.getInstance(document.getElementById('buscarCitaModal')).hide();
    }
});


// Cargar todas las citas al abrir el modal
const modalCitas = document.getElementById('buscarCitaModal');
if (modalCitas) {
    modalCitas.addEventListener('shown.bs.modal', () => {
        fetch(`/cajero/comprobanteDePago/buscar`) // sin ?texto= para traer todas
            .then(res => res.json())
            .then(citas => actualizarTablaCitas(citas))
            .catch(err => console.error('Error al cargar citas:', err));
    });
}
