# teste-uol

# tecnologias utilizadas
  - Java 11 open-jdk
  - SO Linux - Ubuntu v16.04
  - Spring-boot
  - API Webservice Restfull
  - RestTemplate
  
 # fluxo do funcionamento do sistema
O funcionamento do sistema consiste na execução dos seguintes passos, segue abaixo:
   - passo 1:
        execução da consulta atraves do primeiro endpoint (https://ipvigilante.com) que retornara a
        latitude e longitude do local do IP do solicitante;
   
   - passo 2:
        com as coordenadas geograficas, obtemos do codigo que identifica a location do local encontrado
   
   - passo 3:
        com o codigo de identificacao da location, consegue-se atraves das regras e logica do sistema identificar 
        a temperatura maxima e minima do dia do cadastramento do cliente
 
