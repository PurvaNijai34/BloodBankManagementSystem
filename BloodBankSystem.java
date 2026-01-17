import java.util.Scanner;

// -------------------- BASE CLASS (ABSTRACTION) --------------------
abstract class Person {
    protected String name;
    protected String contact;
    protected char bloodType;   // A, B, O, X (for AB)
    protected char rhesus;       // + or -

    abstract void registerPerson();
    abstract void display();
}

// -------------------- DONOR CLASS (INHERITANCE) --------------------
class Donor extends Person {
    private int fitness;
    private int DNR;

    Donor() {
        fitness = 0;
        DNR = -1;
    }

    public void setDNR(int id) {
        DNR = id;
    }

    public int getDNR() {
        return DNR;
    }

    public int getFitness() {
        return fitness;
    }

    public char getType() {
        return bloodType;
    }

    public char getRhesus() {
        return rhesus;
    }

    // POLYMORPHISM
    @Override
    void registerPerson() {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter Name: ");
        name = sc.next();

        System.out.print("Enter Contact Number: ");
        contact = sc.next();

        System.out.print("Enter fitness state (1-fit, 0-unfit): ");
        fitness = sc.nextInt();

        while (true) {
            System.out.print("Enter blood group (A/B/O/AB): ");
            String str = sc.next();

            if (str.equalsIgnoreCase("AB")) {
                bloodType = 'X';
                break;
            } else if (str.equalsIgnoreCase("A") || str.equalsIgnoreCase("B") || str.equalsIgnoreCase("O")) {
                bloodType = Character.toUpperCase(str.charAt(0));
                break;
            } else {
                System.out.println("Invalid Blood Group!");
            }
        }

        while (true) {
            System.out.print("Enter Rhesus (+/-): ");
            rhesus = sc.next().charAt(0);
            if (rhesus == '+' || rhesus == '-') {
                break;
            } else {
                System.out.println("Invalid input!");
            }
        }
    }

    public void updateDetails() {
        Scanner sc = new Scanner(System.in);
        int ch;

        System.out.println("\n1. Update fitness\n2. Update contact");
        ch = sc.nextInt();

        if (ch == 1) {
            System.out.print("Enter new fitness: ");
            fitness = sc.nextInt();
        } else if (ch == 2) {
            System.out.print("Enter new contact: ");
            contact = sc.next();
        } else {
            System.out.println("Invalid choice");
        }
    }

    @Override
    void display() {
        System.out.println("\nDNR: " + DNR);
        System.out.println("Name: " + name);
        System.out.println("Contact: " + contact);
        System.out.print("Blood Group: ");
        if (bloodType == 'X')
            System.out.print("AB");
        else
            System.out.print(bloodType);
        System.out.println(rhesus);
        System.out.println("Fitness: " + fitness);
    }
}

// -------------------- HOSPITAL CLASS (INHERITANCE) --------------------
class Hospital extends Person {
    private int HPID;

    public void setHPID(int id) {
        HPID = id;
    }

    public int getHPID() {
        return HPID;
    }

    @Override
    void registerPerson() {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter Hospital Name: ");
        name = sc.next();

        System.out.print("Enter Contact Number: ");
        contact = sc.next();
    }

    public void paymentCheck() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Has payment been made? (Y/N): ");
        char ch = sc.next().charAt(0);

        if (ch == 'Y' || ch == 'y')
            System.out.println("Transaction Complete");
        else
            System.out.println("Transaction Incomplete");
    }

    @Override
    void display() {
        System.out.println("\nHPID: " + HPID);
        System.out.println("Hospital: " + name);
        System.out.println("Contact: " + contact);
    }
}

// -------------------- BLOOD PACKET --------------------
class BloodPacket {
    char type;
    char rhesus;
    int expiry;

    BloodPacket() {
        type = '0';
        rhesus = '0';
        expiry = 0;
    }
}

// -------------------- BLOOD BANK --------------------
class BloodBank {
    private Donor[] donors = new Donor[10];
    private Hospital[] hospitals = new Hospital[10];
    private BloodPacket[] packets = new BloodPacket[30];

    private int donorCount = 0;
    private int hospitalCount = 0;
    private int packetCount = 0;

    private int A_pos = 0, A_neg = 0, B_pos = 0, B_neg = 0,
                O_pos = 0, O_neg = 0, AB_pos = 0, AB_neg = 0;

    private int username = 123;
    private int password = 123;

    BloodBank() {
        for (int i = 0; i < 30; i++)
            packets[i] = new BloodPacket();
    }

    public boolean authenticate() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        int u = sc.nextInt();
        System.out.print("Enter password: ");
        int p = sc.nextInt();

        return (u == username && p == password);
    }

    // -------------------- Donor Related --------------------
    public void addDonor() {
        if (donorCount >= 10) {
            System.out.println("Cannot add more donors (limit reached).");
            return;
        }

        donors[donorCount] = new Donor();
        donors[donorCount].registerPerson();
        donors[donorCount].setDNR(donorCount);

        System.out.println("Your DNR is: " + donorCount);
        donorCount++;
    }

    public void updateDonor() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter DNR: ");
        int id = sc.nextInt();

        if (id < 0 || id >= donorCount) {
            System.out.println("Invalid Donor ID");
            return;
        }

        donors[id].updateDetails();
    }

    // -------------------- Hospital Related --------------------
    public void addHospital() {
        if (hospitalCount >= 10) {
            System.out.println("Cannot add more hospitals (limit reached).");
            return;
        }

        hospitals[hospitalCount] = new Hospital();
        hospitals[hospitalCount].registerPerson();
        hospitals[hospitalCount].setHPID(hospitalCount);

        System.out.println("Hospital ID: " + hospitalCount);
        hospitalCount++;
    }

    // -------------------- Donate Blood --------------------
    public void donate() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter DNR: ");
        int id = sc.nextInt();

        if (id < 0 || id >= donorCount) {
            System.out.println("Invalid donor");
            return;
        }

        if (donors[id].getFitness() == 0) {
            System.out.println("Donor not fit!");
            return;
        }

        if (packetCount >= 30) {
            System.out.println("No more storage for blood packets.");
            return;
        }

        BloodPacket bp = packets[packetCount++];
        bp.type = donors[id].getType();
        bp.rhesus = donors[id].getRhesus();

        System.out.print("Enter expiry year: ");
        bp.expiry = sc.nextInt();

        if (bp.type == 'A' && bp.rhesus == '+') A_pos++;
        else if (bp.type == 'A' && bp.rhesus == '-') A_neg++;
        else if (bp.type == 'B' && bp.rhesus == '+') B_pos++;
        else if (bp.type == 'B' && bp.rhesus == '-') B_neg++;
        else if (bp.type == 'O' && bp.rhesus == '+') O_pos++;
        else if (bp.type == 'O' && bp.rhesus == '-') O_neg++;
        else if (bp.type == 'X' && bp.rhesus == '+') AB_pos++;
        else if (bp.type == 'X' && bp.rhesus == '-') AB_neg++;

        System.out.println("Blood donated successfully.");
    }

    // -------------------- REQUEST BLOOD --------------------
    public void requestBlood() {
        Scanner sc = new Scanner(System.in);

        if (hospitalCount == 0) {
            System.out.println("No hospitals registered yet.");
            return;
        }

        System.out.print("Enter Hospital ID (HPID): ");
        int hid = sc.nextInt();

        if (hid < 0 || hid >= hospitalCount) {
            System.out.println("Invalid Hospital ID.");
            return;
        }

        System.out.print("Enter required blood group (A/B/O/AB): ");
        String typeStr = sc.next();
        System.out.print("Enter Rhesus (+/-): ");
        char rh = sc.next().charAt(0);

        char typeChar;
        if (typeStr.equalsIgnoreCase("AB"))
            typeChar = 'X';
        else
            typeChar = Character.toUpperCase(typeStr.charAt(0));

        int counter = 0;

        if (typeChar == 'A' && rh == '+') { if (A_pos > 0) {A_pos--; counter=1;} }
        else if (typeChar == 'A' && rh == '-') { if (A_neg > 0) {A_neg--; counter=1;} }
        else if (typeChar == 'B' && rh == '+') { if (B_pos > 0) {B_pos--; counter=1;} }
        else if (typeChar == 'B' && rh == '-') { if (B_neg > 0) {B_neg--; counter=1;} }
        else if (typeChar == 'O' && rh == '+') { if (O_pos > 0) {O_pos--; counter=1;} }
        else if (typeChar == 'O' && rh == '-') { if (O_neg > 0) {O_neg--; counter=1;} }
        else if (typeChar == 'X' && rh == '+') { if (AB_pos > 0) {AB_pos--; counter=1;} }
        else if (typeChar == 'X' && rh == '-') { if (AB_neg > 0) {AB_neg--; counter=1;} }

        if (counter == 1) {
            System.out.println("Blood issued successfully to Hospital ID: " + hid);
            hospitals[hid].paymentCheck();
        } else {
            System.out.println("Requested blood group not available.");
        }
    }

    // -------------------- SHOW STOCK --------------------
    public void show() {
        System.out.println("\n------ BLOOD STOCK ------");
        System.out.println("A+  : " + A_pos);
        System.out.println("A-  : " + A_neg);
        System.out.println("B+  : " + B_pos);
        System.out.println("B-  : " + B_neg);
        System.out.println("O+  : " + O_pos);
        System.out.println("O-  : " + O_neg);
        System.out.println("AB+ : " + AB_pos);
        System.out.println("AB- : " + AB_neg);
    }
}

// -------------------- MAIN --------------------
public class BloodBankSystem {
    public static void main(String[] args) {
        BloodBank b = new BloodBank();
        Scanner sc = new Scanner(System.in);

        System.out.println("\n--- BLOOD BANK MANAGEMENT SYSTEM ---");

        int attempts = 0;
        while (attempts < 3) {
            if (b.authenticate())
                break;
            System.out.println("Invalid Login");
            attempts++;
        }

        if (attempts == 3) {
            System.out.println("Access Denied");
            return;
        }

        while (true) {
            System.out.println("\n1. Add Donor");
            System.out.println("2. Update Donor");
            System.out.println("3. Add Hospital");
            System.out.println("4. Donate Blood");
            System.out.println("5. Show Stock");
            System.out.println("6. Request Blood (Hospital)");
            System.out.println("7. Exit");

            int ch = sc.nextInt();

            switch (ch) {
                case 1: b.addDonor(); break;
                case 2: b.updateDonor(); break;
                case 3: b.addHospital(); break;
                case 4: b.donate(); break;
                case 5: b.show(); break;
                case 6: b.requestBlood(); break;
                case 7: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice");
            }
        }
    }
}
