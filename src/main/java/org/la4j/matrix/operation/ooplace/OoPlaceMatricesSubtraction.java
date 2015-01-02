package org.la4j.matrix.operation.ooplace;

import org.la4j.iterator.MatrixIterator;
import org.la4j.matrix.Matrix;
import org.la4j.matrix.dense.DenseMatrix;
import org.la4j.matrix.operation.SimpleMatrixMatrixOperation;
import org.la4j.matrix.sparse.ColumnMajorSparseMatrix;
import org.la4j.matrix.sparse.RowMajorSparseMatrix;
import org.la4j.matrix.sparse.SparseMatrix;

public class OoPlaceMatricesSubtraction extends SimpleMatrixMatrixOperation<Matrix> {

    @Override
    public Matrix apply(DenseMatrix a, DenseMatrix b) {
        Matrix result = a.blank();

        for (int i = 0; i < a.rows(); i++) {
            for (int j = 0; j < a.columns(); j++) {
                result.set(i, j, a.get(i, j) - b.get(i, j));
            }
        }

        return result;
    }

    @Override
    public Matrix applySimple(DenseMatrix a, SparseMatrix b) {
        Matrix result = a.copy();
        MatrixIterator it = b.nonZeroIterator();

        while (it.hasNext()) {
            double x = it.next();
            int i = it.rowIndex();
            int j = it.columnIndex();
            result.set(i, j, result.get(i, j) - x);
        }

        return result;
    }

    @Override
    public Matrix applySimple(SparseMatrix a, DenseMatrix b) {
        Matrix result = b.multiply(-1.0);
        MatrixIterator it = a.nonZeroIterator();

        while (it.hasNext()) {
            double x = it.next();
            int i = it.rowIndex();
            int j = it.columnIndex();
            result.set(i, j, result.get(i, j) + x);
        }

        return result;
    }

    @Override
    public Matrix applySimple(SparseMatrix a, SparseMatrix b) {
        Matrix result = a.blank();
        MatrixIterator these = a.nonZeroIterator();
        MatrixIterator those = b.nonZeroIterator();
        MatrixIterator both = these.orElseSubtract(those);

        while (both.hasNext()) {
            double x = both.next();
            int i = both.rowIndex();
            int j = both.columnIndex();
            result.set(i, j, x);
        }

        return result;
    }

    @Override
    public Matrix apply(RowMajorSparseMatrix a, ColumnMajorSparseMatrix b) {
        Matrix result = a.blank();
        MatrixIterator these = a.nonZeroRowMajorIterator();
        MatrixIterator those = b.nonZeroRowMajorIterator();
        MatrixIterator both = these.orElseSubtract(those);

        while (both.hasNext()) {
            double x = both.next();
            int i = both.rowIndex();
            int j = both.columnIndex();
            result.set(i, j, x);
        }

        return result;
    }

    @Override
    public Matrix apply(ColumnMajorSparseMatrix a, RowMajorSparseMatrix b) {
        Matrix result = a.blank();
        MatrixIterator these = a.nonZeroColumnMajorIterator();
        MatrixIterator those = b.nonZeroColumnMajorIterator();
        MatrixIterator both = these.orElseSubtract(those);

        while (both.hasNext()) {
            double x = both.next();
            int i = both.rowIndex();
            int j = both.columnIndex();
            result.set(i, j, x);
        }

        return result;
    }
}
