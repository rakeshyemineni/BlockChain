import java.util.Scanner;

class Block {
    int blockId;
    String blockData;
    String blockType;
    Block prev, next;

    Block(int id, String data, String type) {
        this.blockId = id;
        this.blockData = data;
        this.blockType = type;
    }

    @Override
    public String toString() {
        return "[BlockID: " + blockId + ", Data: " + blockData + ", Type: " + blockType + "]";
    }
}

class BlockChain {
    private Block head, tail;

    public void addBlock(int id, String data, String type) {
        Block newBlock = new Block(id, data, type);
        if (head == null) {
            head = tail = newBlock;
        } else {
            tail.next = newBlock;
            newBlock.prev = tail;
            tail = newBlock;
        }
        System.out.println("Block Added: " + newBlock);
    }

    public void deleteBlock(int id) {
        Block current = head;
        while (current != null) {
            if (current.blockId == id) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }
                System.out.println("Block Deleted: " + current);
                return;
            }
            current = current.next;
        }
        System.out.println("Block ID " + id + " not found!");
    }

    public void printBlocks() {
        if (head == null) {
            System.out.println("No blocks available.");
            return;
        }
        Block current = head;
        System.out.println("Blockchain Data:");
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BlockChain chain = new BlockChain();
        System.out.println("Enter commands: (A: Add, D: Delete, P: Print, Q: Quit)");

        while (true) {
            String input = sc.nextLine().trim();
            String[] parts = input.split("\\s+");
            String cmd = parts[0].toUpperCase();

            if (cmd.equals("A")) {
                if (parts.length < 4) {
                    System.out.println("Usage: A <BlockID> <BlockData> <BlockType>");
                    continue;
                }
                int id = Integer.parseInt(parts[1]);
                String data = parts[2];
                String type = parts[3];
                chain.addBlock(id, data, type);

            } else if (cmd.equals("D")) {
                if (parts.length < 2) {
                    System.out.println("Usage: D <BlockID>");
                    continue;
                }
                int id = Integer.parseInt(parts[1]);
                chain.deleteBlock(id);

            } else if (cmd.equals("P")) {
                chain.printBlocks();

            } else if (cmd.equals("Q")) {
                System.out.println("Exiting...");
                break;

            } else {
                System.out.println("Invalid Command!");
            }
        }
        sc.close();
    }
}
