const apiUrl = 'http://localhost:8080/api/beneficios';

function listarBeneficios() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(beneficios => {
            const list = document.getElementById('beneficios-list');
            list.innerHTML = '';
            beneficios.forEach(beneficio => {
                const li = document.createElement('li');
                li.innerHTML = `
                                <strong> ID do benefício: </strong> ${beneficio.id} </br>
                                <strong> Descrição: </strong> ${beneficio.descricao} </br>
                                <button onclick="deletarBeneficioById('${beneficio.id}')">Deletar</button>`;
                list.appendChild(li);
            });
        })
        .catch(error => console.error('Erro ao listar benefícios:', error));
}

// funcao para criar
document.getElementById('create-beneficio-form').addEventListener('submit', function(e) {
    e.preventDefault();

    const descricao = document.getElementById('descricao').value.toUpperCase();
    const beneficio = { descricao };

    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(beneficio),
    })
    .then(() => {
        listarBeneficios();
        document.getElementById('descricao').value = '';
    })
    .catch(error => console.error('Erro ao criar benefício:', error));
});

// funcao para atualizar
document.getElementById('update-btn').addEventListener('click', function() {
    const id = document.getElementById('update-id').value;
    const descricao = document.getElementById('update-descricao').value.toUpperCase();

    const beneficio = { descricao };

    fetch(`${apiUrl}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(beneficio),
    })
    .then(() => {
        listarBeneficios();
        document.getElementById('update-id').value = '';
        document.getElementById('update-descricao').value = '';
    })
    .catch(error => console.error('Erro ao atualizar benefício:', error));
});

function deletarBeneficioById(id) {
    fetch(`${apiUrl}/${id}`, {
        method: "DELETE"
    })
    .then(() => {
        listarBeneficios();
    })
    .catch(error => console.error("Erro ao deletar o benefício:", error));
}

listarBeneficios();
