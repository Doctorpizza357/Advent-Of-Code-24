import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("input.txt"));
        List<Block> disk = new ArrayList<>();
        boolean space = false;
        int id = 0;
        for (String character : lines.get(0).split("")) {
            int num = Integer.parseInt(character);
            if (space) {
                disk.add(new Block(num, -1));
            } else {
                disk.add(new Block(num, id));
                id++;
            }
            space = !space;
        }

        boolean foundSpace = true;
        int diskPlace = disk.size() - 1;
        while (diskPlace > 0) {
            Block work = disk.get(diskPlace);
            if (work.getId() == -1) {
                diskPlace--;
            } else {
                foundSpace = false;
                for (int i = 0; i < diskPlace; i++) {
                    Block possibleSpace = disk.get(i);
                    if (possibleSpace.getId() == -1) {
                        List<Block> blocks = possibleSpace.fit(work);
                        if (blocks != null) {
                            disk.remove(diskPlace);
                            disk.add(diskPlace, new Block(work.getSize(), -1));
                            disk.remove(i);
                            for (int j = blocks.size() - 1; j > -1; j--) {
                                disk.add(i, blocks.get(j));
                            }
                            foundSpace = true;

                            // Merge space blocks
                            /*
                            int end = disk.size() - 1;
                            for (int l = 0; l < end; l++) {
                                if (disk.get(l).getId() == -1 && disk.get(l + 1).getId() == -1) {
                                    int newSize = disk.get(l).getSize() + disk.get(l + 1).getSize();
                                    disk.remove(l);
                                    disk.remove(l);
                                    disk.add(l, new Block(newSize, -1));
                                    l--;
                                    end--;
                                }
                            }
                            */

                            diskPlace = disk.size() - 1;
                            break;
                        }
                    }
                }
                if (!foundSpace) {
                    diskPlace--;
                }
            }
        }

        for (Block b : disk) {
            if (b.getId() == -1) {
                for (int k = 0; k < b.getSize(); k++) System.out.print(".");
            } else {
                for (int k = 0; k < b.getSize(); k++) System.out.print("" + b.getId());
            }
        }
        System.out.println("");

        // 6288338133779 - Low
        // 6288599492129

        BigInteger count = BigInteger.ZERO;
        int placement = 0;
        for (int i = 0; i < disk.size(); i++) {
            Block block = disk.get(i);
            if (block.getId() != -1) {
                for (int j = 0; j < block.getSize(); j++) {
                    count = count.add(BigInteger.valueOf(placement).multiply(BigInteger.valueOf(block.getId())));
                    placement++;
                }
            } else {
                placement += block.getSize();
            }
        }

        System.out.println(count);
    }
}

class Block {
    private int size = 0;
    private int id = 0;

    public Block(int size, int id) {
        this.size = size;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<Block> fit(Block work) {
        if (work.size > this.size) {
            return null;
        }
        List<Block> newList = new ArrayList<>();
        if (work.size == this.size) {
            newList.add(work);
            return newList;
        } else {
            newList.add(work);
            newList.add(new Block(this.size - work.size, -1));
            return newList;
        }
    }

    public int getSize() {
        return size;
    }
}