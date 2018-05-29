package edu.mum.cs490.project.framework.template;

import edu.mum.cs490.project.domain.CardDetail;
import edu.mum.cs490.project.domain.Order;
import edu.mum.cs490.project.framework.observer.NotifierSubject;
import edu.mum.cs490.project.framework.observer.TransferSubject;

public abstract class TransactionTemplate {


    public final Integer process() {

        Integer resultCode = doTransaction();

        if (resultCode == 1) {
            notifyPurchase();

            transfer();
        }

        additionalAction();

        return resultCode;
    }

    protected abstract Integer doTransaction();

    protected abstract void notifyPurchase();

    protected abstract void transfer();

    protected void additionalAction() {
    }
}
