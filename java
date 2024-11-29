import java.util.Random;
import java.util.Scanner;

public class App {

    // Códigos ANSI para colores
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_WHITE_BRIGHT = "\u001B[97m";

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

    static Object[] listSpaceships() {
        String[] spaceShips = { "Transbordador espacial", "Sonda espacial", "Nave de propulsión nuclear",
                "Sonda solar de velocidad ultra alta", "Nave de propulsión de antimateria",
                "Nave con motor de curvatura" };
        double[] speed = { 28000, 61000, 100000, 600000, 10000000, 1080000000 };
        int[] passengers = { 15, 7, 9, 6, 10, 13 };

        return new Object[] { spaceShips, speed, passengers };
    }

    public static void main(String[] args) {
        var request = new Scanner(System.in);

        
        showMenu(request);

        request.close();
    }

    private static void showMenu(Scanner reqShow) {
        System.out.println(ANSI_PURPLE + """
                
                ----------------------------------------------------
                  BIENVENIDO AL SIMULADOR DE VIAJE INTERPLANETARIO
                ----------------------------------------------------""" + ANSI_RESET);
        pressEnter(reqShow);

        byte optionM;
        double selectedPlanetDistance = -1;
        double selectedShipSpeed = -1;
        double[] resources = { 0, 0 }; // Combustible y oxígeno iniciales

        do {
            System.out.println(ANSI_PURPLE + """
                    ----------------------------------
                             MENÚ DE OPCIONES
                    ----------------------------------""" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "Elija las opciones en orden numérico." + ANSI_RESET);
            System.out.println("1. Seleccionar un planeta de destino.");
            System.out.println("2. Seleccionar una nave espacial.");
            System.out.println("3. Configurar recursos.");
            System.out.println("4. Iniciar la simulación del viaje.");
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
                        System.err.println(ANSI_RED + "Debe seleccionar un planeta antes de elegir una nave espacial." + ANSI_RESET);
                        pressEnter(reqShow);
                    } else {
                        selectedShipSpeed = spaceShip(reqShow, selectedPlanetDistance);
                    }
                    break;
                case 3:
                    configureResources(reqShow, resources);
                    break;
                case 4:
                    if (selectedPlanetDistance <= 0 || selectedShipSpeed <= 0) {
                        System.err.println(ANSI_RED + "Debe seleccionar un planeta y una nave espacial antes de iniciar la simulación." + ANSI_RESET);
                        pressEnter(reqShow);
                    } else {
                        double timeHours = calculateOperations(reqShow, selectedPlanetDistance, selectedShipSpeed);
                        travelSimulation(reqShow, selectedPlanetDistance, selectedShipSpeed, timeHours, resources);
                    }
                    break;
                case 0:
                    break;
                default:
                    System.err.println(ANSI_RED + "Ingrese una opción válida" + ANSI_RESET);
                    pressEnter(reqShow);
                    break;

            }

        } while (optionM != 0);

        System.out.println(ANSI_GREEN + "Gracias por usar el simulador de viaje interplanetario, see you later." + ANSI_RESET);
    }

    private static double choosePlanets(Scanner reqChoose) {
        byte optionP;
        double selectedDistance = -1;

        Object[] planetCore = listPlanets();
        String[] planets = (String[]) planetCore[0];
        double[] distance = (double[]) planetCore[1];
        String[] description = (String[]) planetCore[2];

        do {
            System.out.println(ANSI_PURPLE + """
                    --------------------------------------------------------
                                  MENÚ DE ELECCIÓN DE PLANETAS
                    --------------------------------------------------------
                    """ + ANSI_RESET);
            System.out.println(ANSI_BLUE + "Ingrese el número del planeta al que desea viajar." + ANSI_RESET);

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
                System.out.printf(ANSI_WHITE_BRIGHT + "El planeta de destino elegido es %s.%n", planets[optionP - 1]);
                System.out.printf("La distancia desde la Tierra es de %.1f millones de kilometros.%n",
                        distance[optionP - 1]);
                System.out.printf("Descripción: %s", description[optionP - 1] + ANSI_RESET);
                selectedDistance = distance[optionP - 1];
                pressEnter(reqChoose);
                break;
            } else if (optionP == 0) {
                break;
            } else {
                System.err.println(ANSI_RED + "Ingrese una opción válida" + ANSI_RESET);
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
            System.out.println(ANSI_PURPLE + """
                    --------------------------------------------------------
                              MENÚ DE ELECCIÓN DE NAVES ESPACIALES
                    --------------------------------------------------------
                    """ + ANSI_RESET);
            System.out.println(ANSI_BLUE + "Ingrese el número de la nave espacial con la que desea viajar." + ANSI_RESET);

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

                        if (numPas <= 0) {
                            System.err.println(ANSI_RED + "Error: El número de pasajeros debe ser mayor que 0." + ANSI_RESET);
                            continue;
                        } else {
                            calculateOperations(reqShip, selctPlanetDistance, selectedSpeed);
                            break;
                        }
                    } else {
                        System.err.println(ANSI_RED + "Error: Por favor, ingrese un número válido." + ANSI_RESET);
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
                System.err.println(ANSI_RED + "Opción inválida" + ANSI_RESET);
                pressEnter(reqShip);
            }

        } while (optionS != 0);

        return selectedSpeed;
    }

    // Método para calcular el tiempo del viaje.
    private static double calculateOperations(Scanner reqCalculate, double planetDistance, double shipSpeed) {

        // Cálculo del tiempo de viaje
        double timeHours = (planetDistance * 1_000_000) / shipSpeed;
        double timeDays = timeHours / 24;

        // Cálculo de recursos necesarios para el viaje espacial

        // Calculamos los litros totales necesarios basados en la distancia y velocidad
        double fuelLiters = planetDistance * (shipSpeed / 1_000_000) * 10; // Ajuste base para el consumo

        // El 50% será siempre lo mínimo necesario para el viaje
        double fuelNeeded = 50.0; // Siempre necesitaremos el 50%

        // Calculamos el tanque total necesario (el doble de lo que necesitamos para el
        // viaje)
        double totalTankCapacity = fuelLiters * 2; // El tanque total debe ser el doble

        // El oxígeno necesario se calcula basado en los dias de viaje
        double oxygenNeeded = 50.0; // Siempre necesitaremos el 50%
        double oxygenCubicMeters = timeDays * 10; // 10 metros cúbicos por día
        double totalOxygenCapacity = oxygenCubicMeters * 2; // El tanque total debe ser el doble

        System.out.print(ANSI_GREEN + "");
        System.out.printf("La duración del viaje es de %.3f días equivalentes a %.3f horas.%n", timeDays, timeHours);
        System.out.println(ANSI_BLUE + "\nRECURSOS NECESARIOS PARA EL VIAJE:" + ANSI_WHITE_BRIGHT);
        System.out.printf("Combustible mínimo requerido: %.2f%% (%.2f litros)%n", fuelNeeded, fuelLiters);
        System.out.printf("Capacidad total del tanque de combustible: %.2f litros%n", totalTankCapacity);
        System.out.println(ANSI_YELLOW + "NOTA: El 50% del tanque es el mínimo necesario para completar el viaje." + ANSI_WHITE_BRIGHT);

        System.out.printf("%nOxígeno mínimo requerido: %.2f%% (%.2f metros cúbicos)%n", oxygenNeeded,
                oxygenCubicMeters);
        System.out.printf("Capacidad total del tanque de oxígeno: %.2f metros cúbicos%n", totalOxygenCapacity);
        System.out.println(ANSI_YELLOW +"NOTA: El 50% del tanque es el mínimo necesario para completar el viaje." + ANSI_RESET);

        return timeHours;
    }

    private static void configureResources(Scanner scanner, double[] resources) {
        System.out.println(ANSI_PURPLE + """
                -------------------------------------------------------
                                  CONFIGURAR RECURSOS
                -------------------------------------------------------
                """ + ANSI_RESET);

        double fuel = -1, oxygen = -1;

        do {
            try {
                System.out.print("Ingrese la cantidad de combustible (0-100): ");
                fuel = scanner.nextDouble();
                scanner.nextLine();

                System.out.print("Ingrese la cantidad de oxígeno (0-100): ");
                oxygen = scanner.nextDouble();
                scanner.nextLine();

                if (fuel < 0 || fuel > 100 || oxygen < 0 || oxygen > 100) {
                    System.err.println(ANSI_RED + "Los valores deben estar entre 0 y 100. Intente nuevamente." + ANSI_RESET);
                }
            } catch (Exception e) {
                System.err.println(ANSI_RED + "Entrada inválida. Por favor, ingrese un número." + ANSI_RESET);
                scanner.nextLine();
            }
        } while (fuel < 0 || fuel > 100 || oxygen < 0 || oxygen > 100);

        resources[0] = fuel;
        resources[1] = oxygen;

        System.out.printf("Recursos actualizados: Combustible = %.2f, Oxígeno = %.2f.%n", resources[0], resources[1]);
        pressEnter(scanner);
    }

    // Método para ver el progreso, la cantidad de combustible, oxígeno y tiempo
    // estimado.
    private static void travelSimulation(Scanner reqCalculate, double planetDistance, double shipSpeed,
            double timeHours, double[] resources) {
        System.out.println(ANSI_PURPLE + """
                -------------------------------------------------------
                                  SIMULACIÓN DEL VIAJE
                -------------------------------------------------------
                """ + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Inicio del viaje." + ANSI_RESET);
        pressEnter(reqCalculate);

        double combustible = resources[0]; // Combustible configurado por el usuario
        double oxigeno = resources[1]; // Oxígeno configurado por el usuario

        int totalSteps = 100; // Progreso de 1 a 100
        long sleepTimePerStep = 300; // Tiempo de espera en milisegundos

        // El consumo por paso es exactamente el 50% dividido en los pasos totales
        double combustiblePorPaso = 50.0 / totalSteps; // Consumiremos exactamente el 50% durante el viaje
        double oxigenoPorPaso = 50.0 / totalSteps; // Consumiremos exactamente el 50% durante el viaje

        String nave = "[>"; // Representación gráfica de la nave

        Random random = new Random(); // Generador de eventos aleatorios

        int counter = 0;

        for (int i = 1; i <= totalSteps; i++) {
            counter++;
            try {
                Thread.sleep(sleepTimePerStep);
            } catch (InterruptedException e) {
                System.err.println(ANSI_RED + "Error en el hilo de ejecución: " + e.getMessage() + ANSI_RESET);
                return;
            }

            // Estado medio del viaje
            if (counter == 51) {
                System.out.println(ANSI_GREEN + "Mitad del camino." + ANSI_RESET);
                pressEnter(reqCalculate);
            }

            // Reducir recursos por paso
            combustible -= combustiblePorPaso;
            oxigeno -= oxigenoPorPaso;

            // Generar dos eventos aleatorios
            if (counter == random.nextInt(100) || counter == random.nextInt(100)) {
                // Generar evento aleatorio y actualizar recursos si ocurre
                if (random.nextInt(100) < 50) { // 50% de probabilidad por paso
                    double[] eventImpact = triggerRandomEvent(reqCalculate, random);
                    combustible += eventImpact[0]; // Ajustar combustible según el evento
                    oxigeno += eventImpact[1]; // Ajustar oxígeno según el evento

                    // Asegurarse de que los recursos no excedan límites
                    combustible = Math.min(100.0, Math.max(0.0, combustible));
                    oxigeno = Math.min(100.0, Math.max(0.0, oxigeno));

                    // Mostrar los recursos después del evento
                    System.out.printf("Recursos actuales: Combustible = %.2f%%, Oxígeno = %.2f%%%n", combustible,
                            oxigeno);
                    pressEnter(reqCalculate);
                }
            }

            // Barra de progreso
            double porcentaje = (i * 100.0) / totalSteps;
            String barra = "=".repeat(i) + nave + " ".repeat(totalSteps - i);

            // Calcular tiempo restante
            double distanciaRecorrida = (planetDistance * 1_000_000) * (i * 1.0 / totalSteps);
            double distanciaRestante = (planetDistance * 1_000_000) - distanciaRecorrida;
            double tiempoRestanteHoras = distanciaRestante / shipSpeed;

            int horasRestantes = (int) tiempoRestanteHoras;
            int minutosRestantes = (int) ((tiempoRestanteHoras - horasRestantes) * 60);

            // Limpiar pantalla y mostrar barra actualizada
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.printf(
                    "\r[%s] %.2f%% | Combustible: %.2f%% | Oxígeno: %.2f%% | Tiempo Restante: %02d:%02d horas%n", barra,
                    porcentaje, combustible, oxigeno, horasRestantes, minutosRestantes);

            // Terminar la simulación si los recursos se agotan
            if (combustible < 0 || oxigeno < 0) {
                System.err.println(ANSI_RED + "\nLos recursos se han agotado. El viaje no puede continuar." + ANSI_RESET);
                pressEnter(reqCalculate);
                return;
            }
        }

        // Final del viaje
        System.out.println("\n");
        if (combustible >= 50.0 && oxigeno >= 50.0) {
            System.out.println(ANSI_GREEN + "Llegada al destino. Tienes los recursos necesarios para un viaje de regreso." + ANSI_RESET);
            pressEnter(reqCalculate);
        } else {
            System.out.println(ANSI_YELLOW + "Llegada al destino, pero los recursos son insuficientes para regresar." + ANSI_RESET);
            if (combustible < 50.0) {
                System.out.println(ANSI_RED + String.format("Combustible bajo solo queda: %.2f%% restante.", combustible) + ANSI_RESET);
            }
            if (oxigeno < 50.0) {
                System.out.println(ANSI_RED + String.format("Oxígeno bajo solo queda: %.2f%% restante.", oxigeno) + ANSI_RESET);
            }
            pressEnter(reqCalculate);
        }

    }

    private static double[] triggerRandomEvent(Scanner reqCalculate, Random random) {
        System.out.println(ANSI_YELLOW + "\n¡Un evento ha ocurrido durante el viaje!" + ANSI_RESET);	

        boolean isNegative = random.nextBoolean(); // Determina si es un evento negativo o positivo
        int eventType = random.nextInt(3); // Hay 3 tipos de eventos posibles
        String eventDescription = "";

        double combustibleImpact = 0.0; // Cambios en combustible
        double oxigenoImpact = 0.0; // Cambios en oxígeno

        // Ajustar descripción y efecto según el tipo de evento
        if (isNegative) {
            switch (eventType) {
                case 0:
                    eventDescription = "¡Oh no! Una fuga de oxígeno ha ocurrido.";
                    oxigenoImpact = -10.0; // Oxígeno disminuye
                    break;
                case 1:
                    eventDescription = "¡Cuidado! El combustible está siendo consumido más rápido.";
                    combustibleImpact = -10.0; // Combustible disminuye
                    break;
                case 2:
                    eventDescription = "¡Problema en el sistema! Se ha perdido algo de oxígeno.";
                    oxigenoImpact = -15.0; // Oxígeno disminuye
                    break;
            }
        } else {
            switch (eventType) {
                case 0:
                    eventDescription = "Has encontrado una fuente de oxígeno adicional.";
                    oxigenoImpact = +10.0; // Oxígeno aumenta
                    break;
                case 1:
                    eventDescription = "¡Excelente! El sistema ha optimizado el consumo de combustible.";
                    combustibleImpact = +10.0; // Combustible aumenta
                    break;
                case 2:
                    eventDescription = "¡Suerte! Has encontrado un suministro de oxígeno adicional.";
                    oxigenoImpact = +15.0; // Oxígeno aumenta
                    break;
            }
        }

        // Mostrar el evento al usuario

        System.out.println(eventDescription);
        System.out.printf("Impacto del evento: Combustible = %.2f, Oxígeno = %.2f%n", combustibleImpact, oxigenoImpact);

        // Preguntar al usuario si desea actuar
        System.out.print("¿Interactuas con el evento? (Si/No): ");
        String respuesta = reqCalculate.nextLine().toLowerCase();

        if (respuesta.equals("si")) {
            // Usuario decide actuar
            if (isNegative) {
                System.out.println("Has reparado el problema con éxito. Los recursos han mejorado.");
                combustibleImpact += 10.0; // Recuperación de combustible
                oxigenoImpact += 10.0; // Recuperación de oxígeno
            } else {
                System.out.println("Has aprovechado el evento y maximizado los recursos.");
                combustibleImpact += 5.0; // Mejora extra de combustible
                oxigenoImpact += 5.0; // Mejora extra de oxígeno
            }
            pressEnter(reqCalculate);
        } else {
            // Usuario decide no tomar acción
            System.out.println("Has decidido no tomar acción.");

            if (isNegative) {
                // Si es un evento negativo y no actúa, no se cambia nada (por ahora)
                System.out.println("Los recursos permanecen igual.");
            } else {
                // Si es un evento positivo y no actúa, se restan recursos
                System.out.println("Los recursos han disminuido por no aprovechar el evento.");
                combustibleImpact -= 5.0; // Disminuye combustible
                oxigenoImpact -= 5.0; // Disminuye oxígeno
            }
            pressEnter(reqCalculate);
        }

        // Retornar los cambios en los recursos
        return new double[] { combustibleImpact, oxigenoImpact };

    }

    // Método para esperar a que el usuario presione ENTER para continuar.
    private static void pressEnter(Scanner scanner) {
        System.out.println(ANSI_CYAN + "\nPresiona ENTER para continuar.");
        System.out.print("-> " + ANSI_RESET);
        scanner.nextLine();
    }
}

