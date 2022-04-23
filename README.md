[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/joaoplay16/marvel-comics-info/blob/main/README.en.md)
  
[![LinkedIn][linkedin-shield]][linkedin-url]
[![MIT License][license-shield]][license-url]
# Mavel Comics Info

## Sobre o projeto
> App client para API Marvel

Marvel Comics Info é um app com informações sobre os quadrinhos Marvel. 
Este projeto foi criado com o UI kit Jetpack Compose, as informações são obtidas com a ajuda da biblioteca [Retrofit](https://square.github.io/retrofit/), paginadas com [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=pt-br) e persistidas com [Room](https://developer.android.com/training/data-storage/room) e [Datastore](https://developer.android.com/topic/libraries/architecture/datastore?hl=pt-br).

## Screenshots
<img width="300" src="https://github.com/joaoplay16/marvel-comics-info/raw/main/screenshots/screen-1.png"/>
<img width="300" src="https://github.com/joaoplay16/marvel-comics-info/raw/main/screenshots/screen-2.png"/>

## Bibliotecas utilizadas
- [Room](https://developer.android.com/training/data-storage/room)
- [Retrofit](https://square.github.io/retrofit/)
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=pt-br)
- [Dagger - Hilt](https://dagger.dev/hilt/)
- [Coil](https://coil-kt.github.io/coil/)
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore?hl=pt-br)

## Começando
Siga as instruções para conseguir executar o projeto corretamente

1. Obtenha as credenciais em: https://developer.marvel.com/documentation/getting_started
2. Insira as respectivas credenciais no arquivo gradle.properties
3. Syncronize o projeto.
```
API_KEY = "YOUR_API_KEY"  
PRIVATE_KEY = "YOUR_PRIVATE_KEY"
```
## Licença
Distribuído sob a licença Apache. Ver `LICENSE` para mais informações.

## Contato
joaoplay16@gmail.com

[linkedin-url]: https://www.linkedin.com/in/joao-pedro-de-freitas/
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://raw.githubusercontent.com/joaoplay16/marvel-comics-info/main/LICENSE.txt
