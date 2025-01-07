const apiUrl = "http://localhost:8080/api/cartoes";

function listarCartoes() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(cartoes => {
            const cartoesList = document.getElementById("cartoes-list");
            cartoesList.innerHTML = "";
            cartoes.forEach(cartao => {
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


document.getElementById("create-cartao-form").addEventListener("submit", function (event) {
    event.preventDefault();

    const nome = document.getElementById("nome").value.toUpperCase();
    const bandeira = document.getElementById("bandeira").value.toUpperCase();
    const nivelCartao = document.getElementById("nivelCartao").value.toUpperCase();
    const beneficiosSelect = document.getElementById("beneficios");
    const beneficios = Array.from(beneficiosSelect.selectedOptions).map(option => option.value);

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
            listarCartoes();
        })
        .catch(error => console.error("Erro ao criar o cartão:", error));
        location.reload()
});



document.getElementById("update-cartao-form").addEventListener("submit", function (event) {
    event.preventDefault();

    const id = document.getElementById("id-update").value;
    const nome = document.getElementById("nome-update").value.trim();
    const bandeira = document.getElementById("bandeira-update").value.trim();
    const nivelCartao = document.getElementById("nivelCartao-update").value.trim();
    const beneficiosUpdateSelect = document.getElementById("beneficios-update");
    const beneficios = Array.from(beneficiosUpdateSelect.selectedOptions).map(option => option.value);

    const updatedCartao = {};
    if (nome) updatedCartao.nome = nome.toUpperCase();
    if (bandeira) updatedCartao.bandeira = bandeira.toUpperCase();
    if (nivelCartao) updatedCartao.nivelCartao = nivelCartao.toUpperCase();
    if (beneficios.length > 0) updatedCartao.beneficios = beneficios;

    fetch(`${apiUrl}/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(updatedCartao)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao atualizar o cartão.");
            }
            return response.json();
        })
        .then(() => {
            listarCartoes();
        })
        .catch(error => console.error("Erro ao atualizar o cartão:", error));
        location.reload()
});



function deletarCartaoById(id) {
    fetch(`${apiUrl}/${id}`, {
        method: "DELETE"
    })
        .then(() => {
            listarCartoes();
        })
        .catch(error => console.error("Erro ao deletar o cartão:", error));
        location.reload()
}

const apiBeneficios = 'http://localhost:8080/api/beneficios'

function selectBeneficios() {
    const lugarBeneficios = document.getElementById("beneficios");
    const lugarBeneficiosUpdate = document.getElementById("beneficios-update");
    fetch(apiBeneficios)
        .then(response => response.json())
        .then(beneficios => {
            if(beneficios.length == 0){
                lugarBeneficiosUpdate.innerHTML += `
                <option value="" disabled>Não há benefícios cadastrados.</option>
            `;
                lugarBeneficios.innerHTML += `
                <option value="" disabled>Não há benefícios cadastrados.</option>
            `;
            }
            beneficios.forEach(beneficio => {
                lugarBeneficiosUpdate.innerHTML += `
                    <option value="${beneficio.id}">${beneficio.descricao}</option>
                `;
                lugarBeneficios.innerHTML += `
                    <option value="${beneficio.id}">${beneficio.descricao}</option>
                `;
            });
        })
        .catch(error => console.error("Erro ao carregar benefícios:", error));
}

function verIdsCartoesUpdate(){
    const lugarIdCartoesUpdate = document.getElementById("id-update")
    fetch(apiUrl).then(response => response.json()).then(cartoes => {
        if(cartoes.length == 0){
            lugarIdCartoesUpdate.innerHTML += `<option value="" disabled>Não há cartões cadastrados.</option>`
        }
        cartoes.forEach(cartao => {
            lugarIdCartoesUpdate.innerHTML += `<option value="${cartao.id}">${cartao.id}</option>`
        })
    })
}

listarCartoes()
selectBeneficios()
verIdsCartoesUpdate()