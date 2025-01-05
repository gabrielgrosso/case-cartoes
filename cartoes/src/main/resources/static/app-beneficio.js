const apiUrl = 'http://localhost:8080/api/beneficios';  // URL da API

// Função para listar os benefícios
function listarBeneficios() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(beneficios => {
            const list = document.getElementById('beneficios-list');
            list.innerHTML = '';
            beneficios.forEach(beneficio => {
                const li = document.createElement('li');
                li.innerHTML = `
                                <strong> ID: </strong> ${beneficio.id} </br>
                                <strong> Descrição: </strong> ${beneficio.descricao} </br>
                                <button onclick="deletarBeneficioById('${beneficio.id}')">Deletar</button>`;
                list.appendChild(li);
            });
        })
        .catch(error => console.error('Erro ao listar benefícios:', error));
}

// Função para criar um benefício
document.getElementById('create-beneficio-form').addEventListener('submit', function(e) {
    e.preventDefault();

    const descricao = document.getElementById('descricao').value;
    const beneficio = { descricao };

    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(beneficio),
    })
    .then(() => {
        listarBeneficios(); // Recarrega a lista após a criação
        document.getElementById('descricao').value = ''; // Limpa o campo de entrada
    })
    .catch(error => console.error('Erro ao criar benefício:', error));
});

// Função para atualizar um benefício
document.getElementById('update-btn').addEventListener('click', function() {
    const id = document.getElementById('update-id').value;
    const descricao = document.getElementById('update-descricao').value;

    const beneficio = { descricao };

    fetch(`${apiUrl}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(beneficio),
    })
    .then(() => {
        listarBeneficios(); // Recarrega a lista após a atualização
        document.getElementById('update-id').value = '';
        document.getElementById('update-descricao').value = '';
    })
    .catch(error => console.error('Erro ao atualizar benefício:', error));
});

// Função para deletar um benefício
function deletarBeneficioById(id) {
    fetch(`${apiUrl}/${id}`, {
        method: "DELETE"
    })
    .then(() => {
        listarBeneficios();
    })
    .catch(error => console.error("Erro ao deletar o benefício:", error));
}

// Carregar os benefícios ao iniciar
listarBeneficios();
