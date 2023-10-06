import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Paciente {
    private static int contadorPacientes = 1;
    private int numeroPaciente;
    private String nombre;
    private String apellido;

    public Paciente(String nombre, String apellido) {
        this.numeroPaciente = contadorPacientes++;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getNumeroPaciente() {
        return numeroPaciente;
    }

    @Override
    public String toString() {
        return "Paciente #" + numeroPaciente + ": " + nombre + " " + apellido;
    }
}

class Doctor {
    private static int contadorDoctores = 1;
    private int numeroDoctor;
    private String nombre;
    private String apellido;
    private String especialidad;

    public Doctor(String nombre, String apellido, String especialidad) {
        this.numeroDoctor = contadorDoctores++;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
    }

    public int getNumeroDoctor() {
        return numeroDoctor;
    }

    @Override
    public String toString() {
        return "Doctor #" + numeroDoctor + ": " + nombre + " " + apellido + " (Especialidad: " + especialidad + ")";
    }
}

class Cita {
    private String fecha;
    private String hora;
    private String motivo;
    private Paciente paciente;
    private Doctor doctor;

    public Cita(String fecha, String hora, String motivo, Paciente paciente, Doctor doctor) {
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.paciente = paciente;
        this.doctor = doctor;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    @Override
    public String toString() {
        return "Cita para " + paciente + " con " + doctor + "\nFecha: " + fecha + "\nHora: " + hora + "\nMotivo: " + motivo;
    }
}

public class ProgramaCitasMedicas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Paciente> pacientes = new ArrayList<>();
        ArrayList<Doctor> doctores = new ArrayList<>();
        ArrayList<Cita> citas = new ArrayList<>();

        while (true) {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Alta Doctor");
            System.out.println("2. Alta Paciente");
            System.out.println("3. Crear Cita");
            System.out.println("4. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre del doctor:");
                    String nombreDoctor = scanner.nextLine();
                    System.out.println("Ingrese el apellido del doctor:");
                    String apellidoDoctor = scanner.nextLine();
                    System.out.println("Ingrese la especialidad del doctor:");
                    String especialidadDoctor = scanner.nextLine();

                    Doctor nuevoDoctor = new Doctor(nombreDoctor, apellidoDoctor, especialidadDoctor);
                    doctores.add(nuevoDoctor);
                    System.out.println("Doctor registrado con éxito:\n" + nuevoDoctor);
                    break;
                case 2:
                    System.out.println("Ingrese el nombre del paciente:");
                    String nombrePaciente = scanner.nextLine();
                    System.out.println("Ingrese el apellido del paciente:");
                    String apellidoPaciente = scanner.nextLine();

                    Paciente nuevoPaciente = new Paciente(nombrePaciente, apellidoPaciente);
                    pacientes.add(nuevoPaciente);
                    System.out.println("Paciente registrado con éxito:\n" + nuevoPaciente);
                    break;
                case 3:
                    if (doctores.isEmpty() || pacientes.isEmpty()) {
                        System.out.println("Debe haber al menos un doctor y un paciente registrado.");
                        break;
                    }
                    System.out.println("Lista de Doctores:");
                    for (Doctor doctor : doctores) {
                        System.out.println(doctor.getNumeroDoctor() + ". " + doctor);
                    }
                    System.out.println("Elija el número del doctor:");
                    int numeroDoctorSeleccionado = scanner.nextInt();
                    Doctor doctorSeleccionado = doctores.get(numeroDoctorSeleccionado - 1);

                    System.out.println("Lista de Pacientes:");
                    for (Paciente paciente : pacientes) {
                        System.out.println(paciente.getNumeroPaciente() + ". " + paciente);
                    }
                    System.out.println("Elija el número del paciente:");
                    int numeroPacienteSeleccionado = scanner.nextInt();
                    Paciente pacienteSeleccionado = pacientes.get(numeroPacienteSeleccionado - 1);

                    System.out.println("Ingrese la fecha (día/mes/año):");
                    String fechaCita = scanner.next();
                    System.out.println("Ingrese la hora (2 pm - 8 pm):");
                    String horaCita = scanner.next();
                    System.out.println("Ingrese el motivo de la cita:");
                    scanner.nextLine(); // Consumir la nueva línea
                    String motivoCita = scanner.nextLine();

                    // Validar la hora y la fecha
                    boolean horaOcupada = false;
                    for (Cita cita : citas) {
                        if (cita.getFecha().equals(fechaCita) && cita.getHora().equals(horaCita)) {
                            horaOcupada = true;
                            break;
                        }
                    }

                    if (horaOcupada) {
                        System.out.println("La hora seleccionada está ocupada. Elija otra hora.");
                    } else {
                        Cita nuevaCita = new Cita(fechaCita, horaCita, motivoCita, pacienteSeleccionado, doctorSeleccionado);
                        citas.add(nuevaCita);
                        System.out.println("Cita creada con éxito:\n" + nuevaCita);
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    guardarCitasEnArchivo(citas); // Guardar citas en archivo antes de salir
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
    }

    // Método para guardar las citas en un archivo de texto
    private static void guardarCitasEnArchivo(ArrayList<Cita> citas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("citas.txt"))) {
            for (Cita cita : citas) {
                writer.write(cita.getFecha() + "," + cita.getHora() + "," + cita.getMotivo() + ","
                        + cita.getPaciente().getNumeroPaciente() + "," + cita.getDoctor().getNumeroDoctor());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
