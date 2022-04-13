import domain.Carro;
import domain.Cliente;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class LocadoraApplication {

    public static Stack<String> logEmprestimos = new Stack<>();

    public static void main(String[] args) {
        Carro[] frota = new Carro[5];

        Carro modeloCarro = new Carro("ABC-1234", "Toyota", "SW4", "Cinza", 250, true);
        //Arrays.fill(frota, modeloCarro);

        frota[0] = new Carro("ABC-000" + 0, "Toyota", "Cinza Chumbo", "SW" + 0, 964, true);
        frota[1] = new Carro("ABC-000" + 1, "Toyota", "Cinza Chumbo", "SW" + 1, 123, true);
        frota[2] = new Carro("ABC-000" + 2, "Toyota", "Cinza Chumbo", "SW" + 2, 1096, true);
        frota[3] = new Carro("ABC-000" + 3, "Toyota", "Cinza Chumbo", "SW" + 3, 80, true);
        frota[4] = new Carro("ABC-000" + 4, "Toyota", "Cinza Chumbo", "SW" + 4, 333, true);

        frota = ordenarVeiculos(frota);

        System.out.println(Arrays.toString(frota));


        Queue<Cliente> filaEspera = new ArrayDeque<>();

        //Cliente cliente1 = new Cliente("Diego", "1198765432", "Rua do Diego", frota[0]);
        filaEspera.add(new Cliente("Diego", "1198765432", "Rua do Diego", frota[0]));
        filaEspera.offer(new Cliente("Rubens", "217645322", "Rua do Rubens", frota[2]));
        filaEspera.add(new Cliente("Jorge", "217645322", "Rua do Jorge", frota[4]));

        //Forma 0: for (simples)
        for (int i = 0; i < filaEspera.size(); i++){
            //faça o que você quiser
            //System.out.println("Nome: " + filaEspera.remove().getNome());
        }

        //Forma 1: for
        for (Cliente cliente : filaEspera) {
            //faça o que você quiser
            System.out.println("Nome: " + cliente.getNome());
            //System.out.println("Modelo: " + cliente.getCarroFavorito().getModelo());

            emprestarCarro(cliente, frota[0]);

            //Caso queira deixar um intervalo de tempo entre as iterações, basta descomentar o código abaixo
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

        //Forma 2: Iterator
        Iterator<Cliente> iterator = filaEspera.iterator();

        while(iterator.hasNext()) {
            //fazer qualquer coisa
            System.out.println("Nome: " + iterator.next().getNome());
        }

        //Forma 3: Iterator + lambda
        filaEspera.iterator().forEachRemaining(/*fazer qualquer coisa*/ cliente -> System.out.println("Cliente: " + cliente.toString()));

        //Forma 4: Iterator + lambda+
        filaEspera.iterator().forEachRemaining((x) -> {
            System.out.println(x.getNome());
            System.out.println(x.getCarroFavorito().toString());
        });

        //Forma 5: stream
        filaEspera.stream().forEach(cliente -> System.out.println(cliente.getNome()));
        // agora com lambda+
        filaEspera.stream().forEach((cliente) -> {
            System.out.println(cliente.getNome());
            System.out.println(cliente.getCarroFavorito().toString());

            //emprestarCarro(cliente, frota[0]);
        });

        //Buscar na fila
        List<Cliente> c1 = filaEspera.stream()
                .filter(cliente -> cliente.getNome() == "Diego")
                .collect(Collectors.toList());



        //Imprimindo Pilha
        //logEmprestimos.stream().forEach(x -> System.out.println(x));

        int tamanho = logEmprestimos.size();
        for (int i = 0; i < tamanho; i++) {
            System.out.println(logEmprestimos.pop());
        }

        /*System.out.println(logEmprestimos.pop());
        System.out.println(logEmprestimos.pop());
        System.out.println(logEmprestimos.pop());*/

    }

    //Exemplo de fila passada como parâmetro
    public static void imprimirFilaEspera(Queue<Cliente> fila){
        for (Cliente cliente : fila) {
            //faça o que você quiser
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Modelo: " + cliente.getCarroFavorito().getModelo());
        }
    }

    public static Carro[] ordenarVeiculos(Carro[] veiculos) {
        Arrays.sort(veiculos);

        return veiculos;
    }

    public static void emprestarCarro(Cliente cliente, Carro carro) {
        carro.setDisponivel(false);

        //EXEMPLO COM MANIPULAÇÃO DE DATAS - BÔNUS!!
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à's' HH:mm:ss"); //formatar a data como 12/04/2022 às 16:37:15

        logEmprestimos.push(dateFormat.format(Calendar.getInstance().getTime()) + ": Carro " + carro.getMarca() + ", " + carro.getModelo() + ", " + carro.getPlaca() + ", foi EMPRESTADO para " + cliente.getNome());
    }

    public static void devolverCarro(Cliente cliente, Carro carro) {
        carro.setDisponivel(true);

        //EXEMPLO COM MANIPULAÇÃO DE DATAS - BÔNUS!!
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à's' HH:mm:ss"); //formatar a data como 12/04/2022 às 16:37:15

        logEmprestimos.push(dateFormat.format(Calendar.getInstance().getTime()) + ": Carro " + carro.getMarca() + ", " + carro.getModelo() + ", " + carro.getPlaca() + ", foi DEVOLVIDO para " + cliente.getNome());
    }
}
