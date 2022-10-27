package simulator;
import java.util.*;
public class Cache {
        int block_size;
        int num_blocks;
        Queue<Integer> LRU;

        CacheLine[] cacheLines;
        public Cache(int max_size, int block_size, int num_blocks){
            this.block_size = block_size;
            this.num_blocks = num_blocks;

            this.cacheLines = new CacheLine[max_size];
            this.LRU = new LinkedList<>();

            for (int i = 0; i < max_size; i++){
                this.cacheLines[i] = new CacheLine(num_blocks, block_size);
                this.LRU.add(i);
            }

        }
        public void placeBlock(DRAM mem, int addr) {
            // remove and add back to queue
            int line = this.LRU.remove();
            this.LRU.add(line);

            int tag = addr;
            this.cacheLines[line].setTag(tag);

            this.cacheLines[line].setFirst_block_address(addr);
            int offset = 0;
            int offset_addr = addr;
            for (int i = 0; i < this.num_blocks; i++) {
                int[] mem_data = mem.fetchBinaryValue(offset_addr + offset);
                this.cacheLines[line].cache_blocks[i].writeData(mem_data);

                offset = offset + mem.wordSize;
            }
            this.cacheLines[line].valid = 1;
            System.out.println("BLOCKS PLACED");
            String s = String.format("Line used=%d, Address=%d, Offset %d", line, addr, offset);
            System.out.println(s);
        }

        public int[] search(DRAM mem, int addr) {
            for (int i = 0; i < this.cacheLines.length; i++) {
                int max_offset_addr = this.cacheLines[i].tag + 48;
                if (this.cacheLines[i].valid == 1){
                    if (this.cacheLines[i].tag == addr || ( addr > this.cacheLines[i].tag && addr <= max_offset_addr)) {
                        int first_addr = this.cacheLines[i].first_block_address;
                        int offset = (addr - first_addr) % this.num_blocks;

                        System.out.println("CACHE HIT :P");
                        String s = String.format("Line used=%d, Address=%d, Offset %d", i, addr, offset);
                        System.out.println(s);
                        return this.cacheLines[i].cache_blocks[offset].data;
                    }
                }

            }
            //else its a miss :( and we want to add this address and the following blocks to cache
            System.out.println("CACHE MISS :(");
            this.placeBlock(mem, addr);
            return null;
        }
}

class CacheLine{
    int num_blocks;
    CacheBlock[] cache_blocks;

    int tag;
    int first_block_address;

    int valid;

    public CacheLine(int num_blocks, int block_size) {
        this.num_blocks = num_blocks;
        this.cache_blocks = new CacheBlock[num_blocks];

        for (int i = 0; i < num_blocks; i++) {
            this.cache_blocks[i] = new CacheBlock(block_size);
        }
        this.valid = 0;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public void setFirst_block_address(int addr){
        this.first_block_address = addr;
    }
}

class CacheBlock{
    int size;
    int data[];



    public CacheBlock(int size) {
        this.data = new int[size];
    }

    public void writeData(int[] data) {
        for (int i = 0; i < data.length; i++) {
            this.data[i] = data[i];
        }
    }
}
