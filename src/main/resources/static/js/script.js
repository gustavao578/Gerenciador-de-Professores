const API_URL = '/professores';

document.addEventListener("DOMContentLoaded", carregarProfessores);

function carregarProfessores() {
    fetch(API_URL)
        .then(response => response.json())
        .then(professores => {
            const lista = document.getElementById('lista-professores');
            lista.innerHTML = '';
            professores.forEach(prof => {
                const ativoBadge = prof.ativo ? '' : '<span class="badge-desativado">DESATIVADO</span>';
                const obsHtml = prof.observacoes ? `<p><em>Obs: ${prof.observacoes}</em></p>` : '';
                const card = document.createElement('div');
                card.className = 'card';
                card.innerHTML = `
                    <div class="card-info">
                        <p><strong>${prof.nome}</strong> ${ativoBadge}</p>
                        <p>Matrícula: ${prof.matricula}</p>
                        <p>Disciplina: ${prof.disciplina}</p>
                        ${obsHtml}
                    </div>
                    <div class="btn-group">
                        <button class="btn btn-blue" onclick="editarProfessor(${prof.id})">Alterar</button>
                        <button class="btn btn-red" onclick="excluirProfessor(${prof.id})">Excluir</button>
                    </div>
                `;
                lista.appendChild(card);
            });
        });
}

function mostrarFormulario() {
    document.getElementById('tela-listagem').classList.add('hidden');
    document.getElementById('tela-formulario').classList.remove('hidden');
    document.getElementById('mensagem-erro').style.display = 'none';
    document.getElementById('form-professor').reset();
    document.getElementById('prof-id').value = '';
    document.getElementById('form-titulo').innerText = 'Incluir novo professor';
}

function editarProfessor(id) {
    mostrarFormulario();
    document.getElementById('form-titulo').innerText = `Alterar professor - ID ${id}`;
    fetch(`${API_URL}/${id}`)
        .then(response => response.json())
        .then(prof => {
            document.getElementById('prof-id').value = prof.id;
            document.getElementById('prof-nome').value = prof.nome;
            document.getElementById('prof-matricula').value = prof.matricula;
            document.getElementById('prof-disciplina').value = prof.disciplina;
            document.getElementById('prof-observacoes').value = prof.observacoes || '';
            document.querySelector(`input[name="ativo"][value="${prof.ativo}"]`).checked = true;
        });
}

function voltarListagem() {
    document.getElementById('tela-listagem').classList.remove('hidden');
    document.getElementById('tela-formulario').classList.add('hidden');
    carregarProfessores();
}

function salvarProfessor(event) {
    event.preventDefault();
    const id = document.getElementById('prof-id').value;
    const professor = {
        nome: document.getElementById('prof-nome').value,
        matricula: document.getElementById('prof-matricula').value,
        disciplina: document.getElementById('prof-disciplina').value,
        observacoes: document.getElementById('prof-observacoes').value,
        ativo: document.querySelector('input[name="ativo"]:checked').value === 'true'
    };

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_URL}/${id}` : API_URL;

    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(professor)
    }).then(response => {
        if (response.ok) {
            mostrarMensagemSucesso();
            voltarListagem();
        } else {
            document.getElementById('mensagem-erro').style.display = 'block';
        }
    });
}

function excluirProfessor(id) {
    if (confirm("Deseja realmente excluir o professor?")) {
        fetch(`${API_URL}/${id}`, { method: 'DELETE' }).then(response => {
            if (response.ok) {
                mostrarMensagemSucesso();
                carregarProfessores();
            }
        });
    }
}

function mostrarMensagemSucesso() {
    const msg = document.getElementById('mensagem-sucesso');
    msg.style.display = 'block';
    setTimeout(() => msg.style.display = 'none', 3000);
}
