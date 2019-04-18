# Projeto - Teste Dev Java - UOL

# Autor
  - Julio R.G. Leme
  - email: jleme654@gmail.com
  - Linkedin: https://www.linkedin.com/in/julio-romao-guerreir-leme-2484b054/

# Quais tecnologias utilizadas
  - Java 8
  - SO Linux - Ubuntu v16.04
  - Spring-boot
  - API Webservice Restfull
  - RestTemplate
  - IDE Eclipse
  - Maven
  - Postman   
  - Database MongoDB
  
  Estas tecnologias foram utilizadas devido à portabilidade disponibilizada pela plataforma Java diante
  do que espera dos objetivos do projeto. O tempo é um fator importante no desenvolvimento de uma 
  solução com software, por isto escolhi principalmente o Spring-boot com Restfull API, por 2 motivos:
  primeiro que era premissa, segundo que em relação a integrações de sistema, Rest com este novo advento
  , o Sprint-boot , têm-se mostrado performáticos tanto em relação a tempo de desenvolvimento quanto
  em performance de sistema, sem contar que o consumo de memória ficaria bem menor se fossem adotadas
  tecnologias tradicionais e mais antigos, como o webservice JAX ou SOAP como protocolo, moeda de troca
  de informações.
  O tempo de dev também é um dos motivos para se escolher implantar o "MongoDB" como estrutura de banco de 
  dados. É um database NoSQL e não relacional que facilita e muito o desenvolvimento e o resultado efetivo 
  do que foi trabalhado, seu documento padrão para persistência é o JSON, arquivo utilizado também pelo 
  WS Restfull do projeto , foi instalado um MongoDB local, e para produção a sugestão é a hospedagem 
  em um Cloud AWS (Amazon Web Services).
  
# Estrutura de pacote do sistema
  O Maven foi eleito como o empacotador do projeto, gerador dos projetos compilados que serão utilizados
  tanto em dev quanto em produção nos deploys requeridos.
  
# Execução e teste do sistema
  Para a execução do sistema é necessário executar o build Maven para a geração dos pacotes, e após isto
  subir o server Spring-boot em memória através do comando no prompt (Linux ou Windows) "mvn spring-boot:run"
  Para a atividade do bando de dados local MongoDB é preciso iniciá-lo após sua instalação, os comandos 
  e os nomes das collections, DB, e métodos(CRUD) estão exibidos na classe "MongoDBJDBC" do projeto.
  A base url de teste será a http://localhost:8080/teste-uol . A sugestão para execução dos testes é a
  utilização do Postman ou alguma ferramenta de acesso a endpoints de webservices para a execução dos testes
   * listar os clientes salvos: GET  http://localhost:8080/teste-uol/allclients
   * salvar cliente: POST  http://localhost:8080/teste-uol/save/{nome}/{idade}
   * atualizar cliente: PUT  http://localhost:8080/teste-uol/update/{nome}/{idade}
   * consultar cliente por ID: GET  http://localhost:8080/teste-uol/getclient/{id}
   * remover cliente por ID: DELETE   http://localhost:8080/teste-uol/delete/{id}  
  
# Instruções para deploy em produção
  A ferramenta sugerida para a implementação do sistema em produção seria o AWS, que tem as instruções,
  mecanismos mais portáveis disponibilizados no mercado, apontados com uma das melhores soluções em termos 
  de sistemas em Cloud e alta-performance.
  
# Fluxo do funcionamento da regra do sistema
O funcionamento do sistema consiste na execução dos seguintes passos, segue abaixo:
   - passo 1:
        execução da consulta atraves do primeiro endpoint (https://ipvigilante.com) que retornara a
        latitude e longitude do local do IP do solicitante;
   
   - passo 2:
        com as coordenadas geograficas, obtemos do codigo que identifica a location do local encontrado
   
   - passo 3:
        com o codigo de identificacao da location, consegue-se atraves das regras e logica do sistema identificar 
        a temperatura maxima e minima do dia do cadastramento do cliente
  
   - passo 4:
        e por ultimo passo, tem-se o registro do resultado desta codificação em um arquivo JSON persistido
        no DB escolhido , o MongoDB, de onde partirá os manuseios do CRUD, listando, inserindo, atualizando
        ou excluindo os clientes persistidos.
 
 # Fim
