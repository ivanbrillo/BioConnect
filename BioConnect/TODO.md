
# TODO Daniel
- [ ] Aggiungere \@ApiResponses ovunque
- [ ] Aggiungere \@Schema ovunque
- [x] Risolto problema interazioni, adesso le proteine che interagiscono devono esistere
- [ ] Per risolvere il fatto di mettere solo id nelle interazioni e id/name obbligatori nella creazione, gli unici suggerimenti che ho avuto sono quelli di creare due DTO separati o con un validatore personalizzato, ma non mi convincono entrambi, quindi da fare proprio se obbligatorio, altrimenti mettiamo sempre a mano id e name anche nelle interazioni
  - [ ] Relativo al problema sopra, bug quando inseriamo un'interazione con id esistente ma con nome diverso. Effettua correttamente il collegamento con quell'id, ma il nome quindi risulta superfluo
- [ ] Risolto problema id() con query custom
- [ ] Rimane \<id\>
- [ ] Possono esistere id con stesso nome
- [x] Non restituisce le interazioni nelle custom, non possiamo usare le automatiche perchè fa scan completo
- [ ] Nelle interaction usiamo ProteinGraph, ma nelle interazioni possiamo avere anche altre entità (usiamo campi DrugEnhance ecc)
- [ ] Integrare token JWT per migliorare l'autenticazione