package net.mywk.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class BukkitUtils
{      

        public static final BlockFace[] relativeBlockFaces = new BlockFace[] {
                        BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.DOWN
        };

        /**
         * Returns a list of block locations around the block that are of the type specified by the integer list parameter
         * 
         * @param block
         * @param type
         * @return List of block locations around the block that are of the type specified by the integer list parameter
         */
        public static List<Location> getBlocksNearby(org.bukkit.block.Block block, Set<Material> type) {
                ArrayList<Location> blocks = new ArrayList<Location>();
                for (BlockFace blockFace : relativeBlockFaces) {
                        if (type.contains(block.getRelative(blockFace).getType())) {
                                blocks.add(block.getRelative(blockFace).getLocation());
                        }
                }
                return blocks;
        }
        
        public static boolean foundBlockNearby(org.bukkit.block.Block block, int id) {
            ArrayList<Location> blocks = new ArrayList<Location>();
            for (BlockFace blockFace : relativeBlockFaces) {
                    if (block.getRelative(blockFace).getType().getId() == id) {
                            return true;
                    }
            }
            return false;
    }
        
        public static boolean foundBlockOnSide(org.bukkit.block.Block block, int id, BlockFace blockFace) {
            ArrayList<Location> blocks = new ArrayList<Location>();
                    if (block.getRelative(blockFace).getType().getId() == id) {
                            return true;
            }
            return false;
    }

        public static int getInventoryHolderType(InventoryHolder holder) {
                if (holder instanceof DoubleChest) {
                        return ((DoubleChest)holder).getLocation().getBlock().getTypeId();
                } else if (holder instanceof BlockState) {
                        return ((BlockState)holder).getTypeId();
                } else {
                        return -1;
                }
        }


        public static ItemStack[] compareInventories(ItemStack[] items1, ItemStack[] items2) {
                final ItemStackComparator comperator = new ItemStackComparator();
                final ArrayList<ItemStack> diff = new ArrayList<ItemStack>();
                final int l1 = items1.length, l2 = items2.length;
                int c1 = 0, c2 = 0;
                while (c1 < l1 || c2 < l2) {
                        if (c1 >= l1) {
                                diff.add(items2[c2]);
                                c2++;
                                continue;
                        }
                        if (c2 >= l2) {
                                items1[c1].setAmount(items1[c1].getAmount() * -1);
                                diff.add(items1[c1]);
                                c1++;
                                continue;
                        }
                        final int comp = comperator.compare(items1[c1], items2[c2]);
                        if (comp < 0) {
                                items1[c1].setAmount(items1[c1].getAmount() * -1);
                                diff.add(items1[c1]);
                                c1++;
                        } else if (comp > 0) {
                                diff.add(items2[c2]);
                                c2++;
                        } else {
                                final int amount = items2[c2].getAmount() - items1[c1].getAmount();
                                if (amount != 0) {
                                        items1[c1].setAmount(amount);
                                        diff.add(items1[c1]);
                                }
                                c1++;
                                c2++;
                        }
                }
                return diff.toArray(new ItemStack[diff.size()]);
        }

        public static ItemStack[] compressInventory(ItemStack[] items) {
                final ArrayList<ItemStack> compressed = new ArrayList<ItemStack>();
                for (final ItemStack item : items)
                        if (item != null) {
                                final int type = item.getTypeId();
                                final byte data = rawData(item);
                                boolean found = false;
                                for (final ItemStack item2 : compressed)
                                        if (type == item2.getTypeId() && data == rawData(item2)) {
                                                item2.setAmount(item2.getAmount() + item.getAmount());
                                                found = true;
                                                break;
                                        }
                                if (!found)
                                        compressed.add(new ItemStack(type, item.getAmount(), (short)0, data));
                        }
                Collections.sort(compressed, new ItemStackComparator());
                return compressed.toArray(new ItemStack[compressed.size()]);
        }


        public static byte rawData(ItemStack item) {
                return item.getType() != null ? item.getData() != null ? item.getData().getData() : 0 : 0;
        }

        public static int saveSpawnHeight(Location loc) {
                final World world = loc.getWorld();
                final Chunk chunk = world.getChunkAt(loc);
                if (!world.isChunkLoaded(chunk))
                        world.loadChunk(chunk);
                final int x = loc.getBlockX(), z = loc.getBlockZ();
                int y = loc.getBlockY();
                boolean lower = world.getBlockTypeIdAt(x, y, z) == 0, upper = world.getBlockTypeIdAt(x, y + 1, z) == 0;
                while ((!lower || !upper) && y != 127) {
                        lower = upper;
                        upper = world.getBlockTypeIdAt(x, ++y, z) == 0;
                }
                while (world.getBlockTypeIdAt(x, y - 1, z) == 0 && y != 0)
                        y--;
                return y;
        }


        public static class ItemStackComparator implements Comparator<ItemStack>
        {
                @Override
                public int compare(ItemStack a, ItemStack b) {
                        final int aType = a.getTypeId(), bType = b.getTypeId();
                        if (aType < bType)
                                return -1;
                        if (aType > bType)
                                return 1;
                        final byte aData = rawData(a), bData = rawData(b);
                        if (aData < bData)
                                return -1;
                        if (aData > bData)
                                return 1;
                        return 0;
                }
        }
}