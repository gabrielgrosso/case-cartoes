const apiUrl = "http://localhost:8080/api/cartoes";

function listarCartoes() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(cartoes => {
            const cartoesList = document.getElementById("cartoes-list");
            cartoesList.innerHTML = "";
            cartoes.forEach(cartao => {
                // Se benefícios estiver vazio ou indefinido, exibe "Sem benefícios"
                const beneficios = cartao.beneficios && cartao.beneficios.length > 0
                    ? cartao.beneficios.map(beneficio => beneficio.descricao).join(", ")
                    : "Sem benefícios";

                const li = document.createElement("li");
                li.innerHTML = `
                    <strong>ID do cartão: </strong>${cartao.id} </br>
                    <strong>Nome do cartão: </strong>${cartao.nome} </br>
                    <strong>Bandeira do cartão: </strong>${cartao.bandeira} </br>
                    <strong>Nível do cartão: </strong>${cartao.nivelCartao} </br>
                    <strong>Benefícios:</strong> ${beneficios} </br>
                    <button onclick="deletarCartaoById('${cartao.id}')">Deletar</button>
                `;
                cartoesList.appendChild(li);
            });
        })
        .catch(error => console.error("Erro ao carregar os cartões:", error));
}


// Função para criar um novo cartão
document.getElementById("create-cartao-form").addEventListener("submit", function (event) {
    event.preventDefault();

    const nome = document.getElementById("nome").value.toUpperCase();
    const bandeira = document.getElementById("bandeira").value.toUpperCase();
    const nivelCartao = document.getElementById("nivelCartao").value.toUpperCase();
    const beneficiosInput = document.getElementById("beneficios").value;
    const beneficios = beneficiosInput ? beneficiosInput.split(",").map(id => id.trim()) : []; // Se não tiver valor, será um array vazio

    const newCartao = {
        nome,
        bandeira,
        nivelCartao,
        beneficios
    };

    fetch(apiUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(newCartao)
    })
        .then(() => {
            alert("Cartão criado com sucesso!");
            listarCartoes();
        })
        .catch(error => console.error("Erro ao criar o cartão:", error));
});


// Função para atualizar um cartão
document.getElementById("update-cartao-form").addEventListener("submit", function (event) {
    event.preventDefault();

    const id = document.getElementById("id-update").value;
    const nome = document.getElementById("nome-update").value.toUpperCase();
    const bandeira = document.getElementById("bandeira-update").value.toUpperCase();
    const nivelCartao = document.getElementById("nivelCartao-update").value.toUpperCase();
    const beneficios = document.getElementById("beneficios-update").value.split(",").map(beneficio => beneficio.trim()).filter(beneficio => beneficio !== "");

    const updatedCartao = {
        nome,
        bandeira,
        nivelCartao,
        beneficios: beneficios.length > 0 ? beneficios : []
    };

    fetch(`${apiUrl}/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(updatedCartao)
    })
        .then(response => response.json())
        .then(() => {
            alert("Cartão atualizado com sucesso!");
            listarCartoes();
        })
        .catch(error => console.error("Erro ao atualizar o cartão:", error));
});


// Função para deletar um cartão diretamente da lista
function deletarCartaoById(id) {
    fetch(`${apiUrl}/${id}`, {
        method: "DELETE"
    })
        .then(() => {
            alert("Cartão deletado com sucesso!");
            listarCartoes();
        })
        .catch(error => console.error("Erro ao deletar o cartão:", error));
}

const apiBeneficios = 'http://localhost:8080/api/beneficios'

function verBeneficios() {
    fetch(apiBeneficios).then(response => response.json()).then(beneficios => {
        const list = document.getElementById('beneficios-list');
        list.innerHTML = '';
        beneficios.forEach(beneficio => {
            const li = document.createElement('li');
            li.innerHTML = `
                                <strong> ID: </strong> ${beneficio.id} </br>
                                <strong> Descrição: </strong> ${beneficio.descricao} </br>
                                <button onclick="deletarBeneficioById('${beneficio.id}')">Deletar</button>
                           `;
            list.appendChild(li);
        });
        list.innerHTML += `<button onclick="esconderBeneficios()">Esconder benefícios</button>`
    })
        .catch(error => console.error('Erro ao listar benefícios:', error));

}

function esconderBeneficios(){
    const list = document.getElementById('beneficios-list');
    list.innerHTML = '';
}

listarCartoes()