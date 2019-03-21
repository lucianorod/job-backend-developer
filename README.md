# OAuth2

Este sistema de login é baseado no protocolo OAuth2.

A escolha por este protocolo foi motivada por entender que a arquitetura proposta é baseada em microserviços, acredito que seja fundamental ter um sistema de login desacoplado. Onde todas as chamadas, independente de microsserviço, podem ser validadas anterioremente neste serviço.


# Acesso

*Usuário*: admin

*Senha*: admin

Através deste usuário é possível obter um token de *ADMIN* e criar novos usuários.

# Token

Para obter um token de *ADMIN* é necessário realizar a chamada abaixo:

```
curl -X POST \
  http://localhost:8060/authentication/oauth/token \
  -H 'Authorization: Basic cG9ydGFsLXdlYjpwb3J0YWwtd2Vi' \
  -H 'content-type: multipart/form-data' \
  -F grant_type=password \
  -F username=admin \
  -F password=admin
```

# Criação de um novo usuário

Exemplo de chamada:

```
curl -X POST \
  http://localhost:8060/authentication/users/ \
  -H 'Authorization: Bearer {{token}}' \
  -H 'Content-Type: application/json' \
  -d '{
    "cpf": "93534512394",
    "password": "test",
    "roles": [
        {
            "name": "ROLE_USER"
        }
    ],
    "username": "lucianorod"
}'
```

# Desafio

* Imagine que hoje tenhamos um sistema de login e perfis de usuários. O sistema conta com mais de 10 milhões de usuários, sendo que temos um acesso concorrente de cerca de 5 mil usuários. Hoje a tela inicial do sistema se encontra muito lenta. Nessa tela é feita uma consulta no banco de dados para pegar as informações do usuário e exibi-las de forma personalizada. Quando há um pico de logins simultâneos, o carregamento desta tela fica demasiadamente lento. Na sua visão, como poderíamos iniciar a busca pelo problema, e que tipo de melhoria poderia ser feita?

R: Utilizando o protocolo OAuth2 temos um sistema de autenticação e autorização completamente distribuído, sem estado e escalável.
Considerando isso, poderíamos ter um balanceador de carga distribuindo as chamadas entre várias instâncias deste serviço.

Imagino que os serviços estejam rodando em containers orquestrados através do kubernetes, o que permitiria que este serviço fosse escalado de acordo com a demanda de requisições.

É importante ressaltar que após o primeiro login os dados do usuário já estariam em cache (Redis), o que permitiria uma resposta ainda mais rápida e diminuiria a carga sobre o banco de dados. 

* Com base no problema anterior, gostaríamos que você codificasse um novo sistema de login para muitos usuários simultâneos e carregamento da tela inicial. Lembre-se que é um sistema web então teremos conteúdo estático e dinâmico. Leve em consideração também que na empresa existe um outro sistema que também requisitará os dados dos usuários, portanto, este sistema deve expor as informações para este outro sistema de alguma maneira.

R: Listo abaixo algumas medidas que podem ser tomadas pra evitar que o carregamento da tela inicial fique lento:

1) O username do usuário é único e está indexado no banco de dados.
2) Após o primeiro login, os dados básicos do usuários já estão em cache.
3) Auto scalling sob demanda no kubenetes.
4) Cache HTTP para a página de login se houve necessidade.