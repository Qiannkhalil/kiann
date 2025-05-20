package com.mycompany.game_skills;

import java.util.*;

/**
 *
 * @author Student's Account
 */
public class Game_Skills {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Stack<Integer> LastHP = new Stack<>();
        Random randomizer = new Random();
        Stack<Integer> JinguMasteryDao = new Stack<>();

        String playerName = "Qiann The Great";
        String botName = "Vinze And Friends";

        // Default stats
        int playerHP = 1000;
        int botHP = 5000;
        int botMaxHP = 5000;
        int playerMinDmg = 100;
        int playerMaxDmg = 2000;
        int botMinDmg = 50;
        int botMaxDmg = 75;
        int stunTurns = 0;
        int turnCount = 1;

        int[] worldSavior = {0, 0};

        int attackCounter = 0;
        boolean auraSwordActive = false;

        // Character selection
        System.out.println("Choose a Class Sir " + playerName + ":");
        System.out.println("1. Knight");
        System.out.println("2. Mage");
        System.out.println("3. Ranger");
        System.out.println("4. Warrior");

        String chosenClass = "";
        while (true) {
            System.out.print("Enter your class choice: ");
            chosenClass = scan.nextLine().trim();
            if (chosenClass.equalsIgnoreCase("Knight")) {
                playerHP = 1200;
                playerMinDmg = 150;
                playerMaxDmg = 1800;
                System.out.println("You chose Knight: High HP, balanced damage.");
                break;
            } else if (chosenClass.equalsIgnoreCase("Mage")) {
                playerHP = 800;
                playerMinDmg = 300;
                playerMaxDmg = 2500;
                System.out.println("You chose Mage: Lower HP, high burst damage.");
                break;
            } else if (chosenClass.equalsIgnoreCase("Ranger")) {
                playerHP = 900;
                playerMinDmg = 200;
                playerMaxDmg = 2200;
                System.out.println("You chose Ranger: Fast attacks, moderate HP.");
                break;
            } else if (chosenClass.equalsIgnoreCase("Warrior")) {
                playerHP = 1100;
                playerMinDmg = 180;
                playerMaxDmg = 2000;
                System.out.println("You chose Warrior: Balanced HP and damage.");
                break;
            } else {
                System.out.println("Invalid class choice. Please select Knight, Mage, Ranger, or Warrior.");
            }
        }

        System.out.println("You encountered an enemy!");

        while (playerHP > 0 && botHP > 0) {
            System.out.println("\n    Turn " + turnCount + "   ");

            // Decrease cooldowns
            if (worldSavior[0] > 0) {
                worldSavior[0]--;
            }
            if (worldSavior[1] > 0) {
                worldSavior[1]--;
            }

            // Healing logic
            if (playerHP <= 500 && playerHP < 750) {
                int heal = randomizer.nextInt(51) + 100;
                playerHP += heal;
                if (playerHP > 1000) {
                    playerHP = 1000;
                }
                LastHP.push(heal);
                System.out.println("World Tree Blessing healed you for " + heal + " HP.");
            }

            // Player's turn
            if (turnCount % 2 == 1) {
                if (stunTurns > 0) {
                    System.out.println("Enemy is stunned! You get another turn.");
                    stunTurns--;
                }

                // Jingu Mastery Dao activates when bot HP below 750 and size of stack less than 500
                if (botHP < 750 && JinguMasteryDao.size() < 500) {
                    int enhanceHeal = randomizer.nextInt(100) + 500;
                    playerHP += enhanceHeal;
                    if (playerHP > 1000) {
                        playerHP = 1000;
                    }
                    JinguMasteryDao.push(enhanceHeal);
                    System.out.println("Jingu Mastery Dao activated! You healed " + enhanceHeal + " HP.");
                }

                System.out.println("What would you like to do?");
                System.out.println("Player HP: " + playerHP);
                System.out.println("Bot HP: " + botHP);
                System.out.println(">>> Attack");
                System.out.println(">>> Stun");
                System.out.println(">>> Dance");
                System.out.println(">>> Skip");
                System.out.println(">>> World Savior" + (worldSavior[1] > 0 ? " (Cooldown: " + worldSavior[1] + ")" : ""));

                String in = scan.nextLine().trim();

                if (in.equalsIgnoreCase("Attack")) {
                    int playerDmg = randomizer.nextInt(playerMaxDmg - playerMinDmg + 1) + playerMinDmg;

                    attackCounter++;
                    if (attackCounter >= 3 && !auraSwordActive) {
                        auraSwordActive = true;
                        System.out.println("Aura Sword activated! +500 bonus damage!");
                    }

                    if (auraSwordActive) {
                        playerDmg += 500;
                    }

                    botHP -= playerDmg;
                    System.out.println("You dealt " + playerDmg + " damage to the enemy.");
                } else if (in.equalsIgnoreCase("Stun")) {
                    System.out.println("You stunned the enemy for 2 turns!");
                    stunTurns = 2;
                } else if (in.equalsIgnoreCase("Dance")) {
                    System.out.println("You performed a dance. It's not very effective...");
                } else if (in.equalsIgnoreCase("Skip")) {
                    System.out.println("You skipped your turn.");
                } else if (in.equalsIgnoreCase("World Savior")) {
                    if (worldSavior[1] == 0 && worldSavior[0] == 0) {
                        worldSavior[0] = 3;
                        worldSavior[1] = 2;
                        System.out.println("World Savior activated! Blocking enemy attacks for 3 turns.");
                    } else {
                        System.out.println("Skill is on cooldown or already active.");
                    }
                } else {
                    System.out.println("Invalid action. You lost your turn.");
                }

            } else { // Bot's turn
                if (stunTurns > 0) {
                    System.out.println("Bot is stunned and skips its turn!");
                    stunTurns--;
                } else if (worldSavior[0] > 0) {
                    System.out.println("World Savior blocked the enemy's attack!");
                } else {
                    if (botHP <= botMaxHP / 2 && randomizer.nextInt(100) < 25) {
                        int healAmount = randomizer.nextInt(501) + 500;
                        botHP += healAmount;
                        if (botHP > botMaxHP) {
                            botHP = botMaxHP;
                        }
                        System.out.println("The enemy has triggered healing passive: healed " + healAmount + " HP.");
                    } else {
                        int botDmg = randomizer.nextInt(botMaxDmg - botMinDmg + 1) + botMinDmg;
                        playerHP -= botDmg;
                        System.out.println("The enemy dealt " + botDmg + " damage to you.");
                    }
                }
            }

            turnCount++;
        }

        // End of game messages
        if (playerHP <= 0) {
            System.out.println("\nDefeat! The enemy has slain " + playerName + "!");
        } else if (botHP <= 0) {
            System.out.println("\nVictory! " + playerName + " has slain the enemy! " + botName);
        }

        System.out.println("Heals applied from World Tree Blessing: " + LastHP);
    }
}
                                                                                          