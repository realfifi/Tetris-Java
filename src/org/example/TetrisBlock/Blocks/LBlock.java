package org.example.TetrisBlock.Blocks;

import org.example.TetrisBlock.Block;
import org.example.TetrisBlock.Cell;

import java.awt.*;

public class LBlock extends Block {
    public LBlock() {
        super();
    }

    @Override
    protected void initCells() {
        cells = new Cell[][] {
                {new Cell(2, 0), new Cell(0, 1), new Cell(1, 1), new Cell(2, 1)},
                {new Cell(1, 0), new Cell(1, 1), new Cell(1, 2), new Cell(2, 2)},
                {new Cell(0, 1), new Cell(1, 1), new Cell(2, 1), new Cell(0, 2)},
                {new Cell(0, 0), new Cell(1, 0), new Cell(1, 1), new Cell(1, 2)}
        };
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }
}
