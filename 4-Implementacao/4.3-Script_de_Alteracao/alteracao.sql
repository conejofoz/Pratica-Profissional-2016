UPDATE clientes
   SET id=?, nomecliente=?, idcidade=?, email=?, telefone=?, sexo=?, 
       documento=?, cpf=?, cep=?, endereco=?, numero=?, complemento=?, 
       telefonecomercial=?, celular=?, fax=?, site=?, tipopessoa=?, 
       bairro=?, cnpj=?, datanascimento=?
 WHERE <condition>;
 
 
 
 UPDATE fornecedores
   SET id=?, nomefornecedor=?, documento=?, endereco=?, idcidade=?, 
       email=?, telefone=?, tipopessoa=?, sexo=?, cep=?, numero=?, complemento=?, 
       bairro=?, telefonecomercial=?, celular=?, fax=?, site=?, cpf=?, 
       datanascimento=?, cnpj=?
 WHERE <condition>;



UPDATE produtos
   SET id=?, nomeproduto=?, codigobarras=?, codigodofornecedor=?, quantidadeporcaixa=?, 
       estoqueminimo=?, estoquemaximo=?, estoqueatual=?, pesoneto=?, 
       pesobruto=?, tamanho=?, valorcompra=?, icms=?, iss=?, ipi=?, 
       precocusto=?, valoricms=?, valoriss=?, valoripi=?, precomedio=?, 
       precovarejo=?, precoatacado=?, dataultimacompra=?, dataultimavenda=?, 
       idgrupo=?, idfornecedor=?, idmarca=?, datacadastro=?, horacadastro=?, 
       siglaunidade=?, idncm=?, idcst=?, idcfop=?
 WHERE <condition>;
