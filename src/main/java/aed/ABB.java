package aed;

public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private class Nodo {
        Nodo der, izq, progenitor;
        T data;
        public Nodo(T data){
            this.data = data;
        }
    }
    Nodo raiz;

    private void enlazar_izq(Nodo p, Nodo l){
        p.izq = l;
        if(l != null){
            l.progenitor = p;
        }
    }
    private void enlazar_der(Nodo p, Nodo r){
        p.der = r;
        if(r != null){
            r.progenitor = p;
        }
    }

    private int cardinal(Nodo n){
        if (n == null){
            return 0;
        }
        return 1 + cardinal(n.der) + cardinal(n.izq);
    }

    private boolean pertenece(Nodo n, T elem){
        if (n == null)
            return false;
        int res = elem.compareTo(n.data);
        
        if(res ==  0){
            return true;
        }

        if(res >  0){
            return pertenece(n.der, elem);
        }
        
        return pertenece(n.izq, elem);
    }

    private void insertar(Nodo n, T elem){
        int res = elem.compareTo(n.data);
        
        if(res ==  0){
            return;
        }

        if(res >  0){
            if(n.der == null){
                Nodo r = new Nodo(elem);
                enlazar_der(n, r);
            } else {
                insertar(n.der, elem);
            }
            return;
        }
        
        if(n.izq == null){
            Nodo l = new Nodo(elem);
            enlazar_izq(n, l);
        } else{
            insertar(n.izq, elem);
        }
    }

    private Nodo minimo(Nodo n){
        if (n.izq == null){
            return n;
        }
        return minimo(n.izq);
    }

    private Nodo maximo(Nodo n){
        if (n.der == null){
            return n;
        }
        return maximo(n.der);
    }

    Nodo sucesor(Nodo n){
        if(n == null) return null;
        if(n.der != null){
            return minimo(n.der);
        }

        while(n.progenitor != null && n.progenitor.izq != n){
            n = n.progenitor;
        }

        return n.progenitor;
    }

    private boolean es_nodo_simple(Nodo n){
        return n.der == null || n.izq == null;
    }

    private void eliminar_nodo_simple(Nodo n){
        Nodo progenitor = n.progenitor;
        n.progenitor = null;
        Nodo cria = n.der != null ? n.der : n.izq;
        if(progenitor == null){
            raiz = cria;
            if(cria != null){
                cria.progenitor = null;
            }
        } else if(progenitor.der == n){
            enlazar_der(progenitor, cria);
        } else if(progenitor.izq == n){
            enlazar_izq(progenitor, cria);
        }
    }

    private void eliminar(Nodo n, T elem){
        int res = elem.compareTo(n.data);
        
        if(res ==  0){
            if(es_nodo_simple(n)){
                eliminar_nodo_simple(n);
                return;
            }

            Nodo min = minimo(n.der);
            n.data = min.data;
            eliminar_nodo_simple(min);
        }

        if(res >  0){
            if(n.der == null){
                return;
            }
            eliminar(n.der, elem);
        }

        if(n.izq == null){
            return;
        }
        eliminar(n.izq, elem);
    }

    public ABB() {
    }

    public int cardinal() {
        return cardinal(raiz);
    }

    public T minimo(){
        return minimo(raiz).data;
    }

    public T maximo(){
        return maximo(raiz).data;
    }

    public void insertar(T elem){
        if(raiz == null){
            raiz = new Nodo(elem);
            return;
        }
        insertar(raiz, elem);
    }

    public boolean pertenece(T elem){
        return pertenece(raiz, elem);
    }

    public void eliminar(T elem){
        eliminar(raiz, elem);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Iterador<T> it = iterador();
        
        sb.append("{");
        if (it.haySiguiente()){
            sb.append(it.siguiente());
        }
        while(it.haySiguiente()){
            sb.append(",").append(it.siguiente());
        }
        return sb.append("}").toString();
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual = minimo(raiz);

        public boolean haySiguiente() {
            return _actual != null;
        }
    
        public T siguiente() {
            T res = _actual.data;
            _actual = sucesor(_actual);
        
            return res;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
