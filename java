import java.util.Random;
import java.util.Scanner;

public class App {

    // Método con los planetas, distancias y descripci+on.
    static Object[] listPlanets() {
        String[] planets = { "Venus", "Marte", "Mercurio", "Júpiter", "Saturno", "Urano", "Neptuno" };
        double[] distance = { 42.4, 78.3, 91.7, 628.9, 1284.4, 2721.4, 4345.4 };
        String[] description = {
                "es similar en tamaño a la Tierra, pero con una atmósfera densa y temperaturas extremas.",
                "el planeta rojo tiene un clima frío y valles secos, con signos de agua en el pasado.",
                "es el más cercano al Sol, pequeño y sin atmósfera significativa.",
                "es el planeta más grande del sistema solar con una gran tormenta llamada la Gran Mancha Roja.",
                "tiene impresionantes anillos y es un gigante gaseoso.",
                "es gigante helado con un eje de rotación muy inclinado, dando lugar a estaciones extremas.",
                "planeta más lejano del sol, con fuertes vientos y un color azul intenso debido al metano en su atmósfera." };

        return new Object[] { planets, distance, description };
    }

    // Método con las naves espaciales, sus velocidades y capacidad de pasajeros.
    static Object[] listSpaceships() {
        String[] spaceShips = { "Transbordador espacial", "Sonda espacial", "Nave de propulsión nuclear",
                "Sonda solar de velocidad ultra alta", "Nave de propulsión de antimateria",
                "Nave con motor de curvatura" };
        double[] speed = { 28000, 61000, 100000, 600000, 10000000, 1080000000 };
        int[] passengers = { 15, 7, 9, 6, 10, 13 };

        return new Object[] { spaceShips, speed, passengers };
    }

    // Método principal
    public static void main(String[] args) throws Exception {
        var request = new Scanner(System.in);

        showMenu(request);

        request.close();
    }

    // Menú de opciones.
    private static void showMenu(Scanner reqShow) {
        System.out.println("""
                ----------------------------------------------------
                  BIENVENIDO AL SIMULADOR DE VIAJE INTERPLANETARIO
                ----------------------------------------------------""");
        pressEnter(reqShow);

        byte optionM;
        // Variables para almacenar los datos seleccionados
        double selectedPlanetDistance = -1;
        double selectedShipSpeed = -1;
        do {
            System.out.println("""
                    ----------------------------------
                             MENÚ DE OPCIONES
                    ----------------------------------""");
            System.out.println("Elija las opciones en orden númerico.");
            System.out.println("1. Seleccionar un planeta de destino.");
            System.out.println("2. Seleccionar una nave espacial.");
            System.out.println("3. Iniciar la simulación del viaje.");
            System.out.println("0. Salir del programa.");
            System.out.print("-> ");
            optionM = reqShow.nextByte();
            reqShow.nextLine();

            switch (optionM) {
                case 1:
                    selectedPlanetDistance = choosePlanets(reqShow);
                    break;
                case 2:
                    if (selectedPlanetDistance <= 0) {
                        System.err.println("Debe seleccionar un planeta antes de elegir una nave espacial.");
                        pressEnter(reqShow);
                    } else {
                        selectedShipSpeed = spaceShip(reqShow, selectedPlanetDistance);
                    }
                    break;
                case 3:
                    if (selectedPlanetDistance <= 0 || selectedShipSpeed <= 0) {
                        System.err.println(
                                "Debe seleccionar un planeta y una nave espacial antes de iniciar la simulación.");
                        pressEnter(reqShow);
                    } else {
                        double timeHours = calculateSpeed(reqShow, selectedPlanetDistance, selectedShipSpeed);
                        travelSimulation(reqShow, selectedPlanetDistance, selectedShipSpeed, timeHours);
                    }
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Ingrese una opción válida");
                    pressEnter(reqShow);
                    break;
            }

        } while (optionM != 0);

        System.out.println("Gracias por usar el simulador de viaje interplanetario, see you later.");
    }

    // Método para elegir planeta.
    private static double choosePlanets(Scanner reqChoose) {
        byte optionP;
        double selectedDistance = -1;

        // Obtiene los planetas, sus distancias y descripciones.
        Object[] planetCore = listPlanets();
        String[] planets = (String[]) planetCore[0];
        double[] distance = (double[]) planetCore[1];
        String[] description = (String[]) planetCore[2];

        do {
            System.out.println("""
                    --------------------------------------------------------
                                  MENÚ DE ELECCIÓN DE PLANETAS
                    --------------------------------------------------------
                    """);
            System.out.println("Ingrese el número del planeta al que desea viajar.");

            // Muestra los planetas.
            for (int i = 0; i < planets.length; i++) {
                System.out.printf("%d. %s.%n", i + 1, planets[i]);
            }
            System.out.println("0. Regresar.");
            System.out.print("-> ");
            optionP = reqChoose.nextByte();
            reqChoose.nextLine();

            if (optionP > 0 && optionP <= planets.length) {
                // Muestra al planeta elejido, la distancia y descripción.
                System.out.printf("El planeta de destino elegido es %s.%n", planets[optionP - 1]);
                System.out.printf("La distancia desde la Tierra es de %.1f millones de kilometros.%n",
                        distance[optionP - 1]);
                System.out.printf("Descripción: %s", description[optionP - 1]);
                selectedDistance = distance[optionP - 1];
                pressEnter(reqChoose);
                break;
            } else if (optionP == 0) {
                break;
            } else {
                System.err.println("Ingrese una opción válida");
                pressEnter(reqChoose);
            }

        } while (optionP != 0);

        return selectedDistance;
    }

    // Método para elegir la nave espacial.
    private static double spaceShip(Scanner reqShip, double selctPlanetDistance) {
        byte optionS;
        double selectedSpeed = -1;

        // Obtiene las naves espaciales y sus velocidades.
        Object[] spaceShipsCore = listSpaceships();
        String[] spaceShips = (String[]) spaceShipsCore[0];
        double[] speed = (double[]) spaceShipsCore[1];
        int[] passengers = (int[]) spaceShipsCore[2];

        do {
            System.out.println("""
                    --------------------------------------------------------
                              MENÚ DE ELECCIÓN DE NAVES ESPACIALES
                    --------------------------------------------------------
                    """);
            System.out.println("Ingrese el número de la nave espacial con la que desea viajar.");

            // Muestra las naves disponibles .
            for (int i = 0; i < spaceShips.length; i++) {
                System.out.printf("%d. %s. | velocidad de %.0f km. | Capacidad para %d pasajeros.%n", i + 1,
                        spaceShips[i], speed[i], passengers[i]);
            }

            System.out.println("0. Regresar.");
            System.out.print("-> ");
            optionS = reqShip.nextByte();
            reqShip.nextLine();

            if (optionS > 0 && optionS <= spaceShips.length) {
                selectedSpeed = speed[optionS - 1];

                // Valida que el dato que se ingrese sea un número positivo y que haya al menos
                // un pasajero.
                while (true) {
                    System.out.print("Ingrese la cantidad de pasajeros que viajarán: ");
                    if (reqShip.hasNextInt()) {
                        int numPas = reqShip.nextInt();
                        reqShip.nextLine();

                        if (numPas < 0) {
                            System.out.println("El número debe ser positivo. Inténtelo de nuevo.");
                            pressEnter(reqShip);
                        } else if (numPas == 0) {
                            System.out.println(
                                    "Debe haber almenos un pasajero para realizar el viaje. Inténtelo de nuevo.");
                            pressEnter(reqShip);
                        } else {
                            calculateSpeed(reqShip, selctPlanetDistance, selectedSpeed);
                            break;
                        }
                    } else {
                        System.out.println("Debe ingresar un número. Inténtelo de nuevo.");
                        reqShip.next();
                        reqShip.nextLine();
                        pressEnter(reqShip);
                    }
                }
                pressEnter(reqShip);
                break;

            } else if (optionS == 0) {
                break;
            } else {
                System.err.println("Opción inválida");
                pressEnter(reqShip);
            }

        } while (optionS != 0);

        return selectedSpeed;
    }

    // Método para calcular el tiempo del viaje.
    private static double calculateSpeed(Scanner reqCalculate, double planetDistance, double shipSpeed) {

        double timeHours = (planetDistance * 1_000_000) / shipSpeed;
        double timeDays = timeHours / 24;

        System.out.printf("La duración del viaje es de %.3f días equivalentes a %.3f horas.%n", timeDays, timeHours);
        return timeHours; 
    }

    // Método para ver el progreso, la cantidad de combustible, oxígeno y tiempo estimado.
    private static void travelSimulation(Scanner reqCalculate, double planetDistance, double shipSpeed, double timeHours) {
        double combustible = 100.0; // 100% de combustible al inicio.
        double oxigeno = 100.0; // 100% de oxígeno al inicio.
        
        //Avance del progreso
        int totalSteps = 50; // Progreso 1 a 100 de 2 en dos.
        long sleepTimePerStep = 300; // Tiempo milisegundos si quieres lo puedes agrandar o minimizar.

        // Puede cambiarlos para validar cuando faltan recursos
        double combustiblePorPaso = 50.0 / totalSteps; // Disminuye 0,5% por cada 1 de avance de la nave.
        double oxigenoPorPaso = 50.0 / totalSteps; // Disminuye 0,5% por cada 1% de avance de la nave.

        String nave = "[>"; // La nave xd.
    
        // Generar un objeto Random para los eventos aleatorios.
        Random random = new Random();

        for (int i = 1; i <= totalSteps; i++) {
            try {
                Thread.sleep(sleepTimePerStep);
            } catch (InterruptedException e) {
                System.err.println("Error en el hilo de ejecución: " + e.getMessage());
                return;
            }

            // Quitar recursos.
            combustible -= combustiblePorPaso;
            oxigeno -= oxigenoPorPaso;

            // Calcular distancia recorrida y restante.
            double distancetraveled = (planetDistance * 1_000_000) * (i * 1.0 / totalSteps); // Convertir a km.
            double remainingdistance = (planetDistance * 1_000_000) - distancetraveled; // Convertir a km.

            // Calcular el tiempo restante en horas.
            double remainingtime = remainingdistance / shipSpeed;

            // Convertir el tiempo restante a horas y minutos.
            int remainingHours = (int) remainingtime;
            int remainingMinutes = (int) ((remainingtime - remainingHours) * 60);

            // Barra de progreso
            double percentage = (i * 100.0) / totalSteps;
            String barra = "=".repeat(i) + nave + " ".repeat(totalSteps - i);

            // Limpiar la pantalla
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.print("\r"); // Volver al inicio de la línea
            System.out.printf("[%s] %.2f%% | Combustible: %.2f%% | Oxígeno: %.2f%% | Tiempo Restante: %02d:%02d horas%-20s",
        barra, percentage, combustible, oxigeno, remainingHours, remainingMinutes, "");

            // Generar eventos aleatorios
            if (random.nextInt(100) < 10) { // 10% de probabilidad de que ocurra un evento en cada paso.
                triggerRandomEvent(reqCalculate, random, combustible, oxigeno, combustiblePorPaso, oxigenoPorPaso);
            }

            // Fin de la simulación
            if (combustible <= 0 || oxigeno <= 0) {
                System.err.println("\nLos recursos se han agotado.");
                pressEnter(reqCalculate);
                return; // Fin de la simulación si los recursos se acaban.
            }
        }

        // Recursos al final del viaje.
        System.out.println("\n");
        if (combustible >= 50.0 && oxigeno >= 50.0) {
            System.out.println("Viaje completado con éxito. Tienes los recursos necesarios para un viaje de regreso.");
        } else {
            System.out.println("Viaje completado con éxito, pero los recursos son insuficientes para regresar.");
            if (combustible < 50.0) {
                System.err.printf("Combustible bajo solo queda: %.2f%% restante.%n", combustible);
            }
            if (oxigeno < 50.0) {
                System.err.printf("Oxígeno bajo solo queda: %.2f%% restante.%n", oxigeno);
            }
        }

        pressEnter(reqCalculate);
    }

    private static void triggerRandomEvent(Scanner reqCalculate, Random random, double combustible, double oxigeno,
            double combustibleporpaso, double oxigenoporpaso) {
        System.out.println("\n¡Un evento ha ocurrido durante el viaje!");

        // evento negativo
        if (random.nextBoolean()) {
            int eventLoss = random.nextInt(3); // 3 posibles eventos de pérdida pueden cambiarse las frases
            switch (eventLoss) {
                case 0:
                    System.out.println("¡Oh no! Una fuga de oxígeno ha ocurrido.");
                    oxigenoporpaso += 3.0;
                    break;
                case 1:
                    System.out.println("¡Cuidado! El combustible está siendo consumido más rápido.");
                    combustibleporpaso += 2.0;
                    break;
                case 2:
                    System.out.println("¡Problema en el sistema! Se ha perdido algo de oxígeno.");
                    oxigenoporpaso += 5.0;
                    break;
            }
        } else {
            // Evento positivo
            int eventGain = random.nextInt(3); // 3 posibles eventos de ganancia pueden cambairse las frases
            switch (eventGain) {
                case 0:
                    System.out.println("Has encontrado una fuente de oxígeno adicional.");
                    oxigenoporpaso -= 0.1;
                    break;
                case 1:
                    System.out.println("¡Excelente! El sistema ha optimizado el consumo de combustible.");
                    combustibleporpaso -= 0.25;
                    break;
                case 2:
                    System.out.println("¡Suerte! Has encontrado un suministro de oxígeno adicional.");
                    oxigenoporpaso -= 0.2;
                    break;
            }
        }

        // Asegurarse de que los recursos no superen el 100% ni bajen del 0%
        oxigeno = Math.min(100, Math.max(0, oxigeno));
        combustible = Math.min(100, Math.max(0, combustible));

        // Preguntar al usuario si desea actuar
        System.out.print("¿Deseas repararlo? (Si/No): ");
        String respuesta = reqCalculate.nextLine().toLowerCase();

        if (respuesta.equals("si")) {
            // Acción positiva restaura recursos
            oxigeno = Math.min(100, oxigeno + 10.0);
            combustible = Math.min(100, combustible + 10.0);
            System.out.println("Has decidido reparar el problema, los recursos han aumentado.");
        } else {
            // Respuesta incorrecta
            System.out.println("Has decidido no tomar acción. Los recursos disminuyen debido al daño no reparado.");
            oxigeno -= 5.0;
            combustible -= 5.0;
        }

        // Imprimir los recursos actuales
        System.out.printf("Recursos actuales - Combustible: %.2f%% | Oxígeno: %.2f%%\n", combustible, oxigeno);
        pressEnter(reqCalculate);
    }

    // Método para esperar a que el usuario presione ENTER para continuar.
    private static void pressEnter(Scanner pressRequest) {
        System.out.printf("%nOprima ENTER para continuar.%n");
        System.out.print("-> ");
        pressRequest.nextLine();
    }
}
