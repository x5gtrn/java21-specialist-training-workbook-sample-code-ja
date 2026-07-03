@Entity
class Order {
    @OneToMany(mappedBy = "order",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<OrderLine> lines = new ArrayList<>();

    public void addLine(OrderLine line) {
        lines.add(line);
        line.setOrder(this);
    }

    public void removeLine(OrderLine line) {
        lines.remove(line);
        line.setOrder(null);
    }
}
@@BLOCK@@
@OneToMany(mappedBy = "order",
           cascade = CascadeType.ALL,
           orphanRemoval = true)
private List<OrderLine> lines = new ArrayList<>();