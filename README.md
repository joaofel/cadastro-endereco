# cadastro-endereco

Documentação da Api:

Ao executar o projeto, acessar o end-point: /swagger-ui.html

Foi criado aplicação com as tecnologias:

- JAVA EE 8;
- Spring Boot;
- Spring MVC;
- Spring JPA
- MySQL

Foi escolhido essas tecnologias pela agilidade no desenvolvimento, assim como a facilidade na leitura do codigo e manutencao.
Com poucas linhas de codigo é possivel desenvolver um WebService funcional, seguindo os padros MVC.

Utilizado Heroku para deploy na nuvem:

http://cadastro-endereco.herokuapp.com/swagger-ui.html

(Versao gratuita do heroku a aplicacao dorme a cada 30 minutos de inatividade, apos o proximo acesso a aplicacao demora 1 minuto para o start)



Protocolo HTTP:

O protocolo HTTP é basicamente requisições e respostas entre clientes e servidores. 
O cliente (um dispositivo ou navegador) solicita um recurso, enviado um pacote de dados contendo URI, Headers e etc. O servidor recebe esa informação, nesse tempo o cliente aguarda uma resposta, que pode ser uma pagina ou um conjunto de tags ou outros headers.
A resposta, existe uma identificação muito importante, que são os codigos de respostas, dentro os diversos, os principais sao 2xx (sucesso), 4xx e 5xx(erros)

As requisições HTTP possuem seus metodos, que os principais são:

GET: Solicita um recurso
Post: Cria um recurso
Put: Atualiza um recurso
Delete: deleta um recurso
